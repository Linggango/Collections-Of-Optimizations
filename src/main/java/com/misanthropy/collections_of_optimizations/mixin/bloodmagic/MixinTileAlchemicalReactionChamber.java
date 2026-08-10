package com.misanthropy.collections_of_optimizations.mixin.bloodmagic;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.RecipeCacheGeneration;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import wayoftime.bloodmagic.common.tile.TileAlchemicalReactionChamber;

import java.util.Optional;

@Mixin(value = TileAlchemicalReactionChamber.class, remap = false)
public abstract class MixinTileAlchemicalReactionChamber {

    @Unique
    private RecipeManager.CachedCheck<Container, SmeltingRecipe> coo$smeltingCheck;

    @Unique
    private int coo$smeltingCheckGeneration = -1;

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;",
                    remap = true
            ),
            require = 0
    )
    private Optional<?> coo$cachedSmeltingLookup(RecipeManager manager,
                                                 RecipeType<?> type,
                                                 Container container,
                                                 Level level,
                                                 Operation<Optional<?>> original) {
        if (!CoOConfig.bloodmagicCacheArcFurnaceRecipe || type != RecipeType.SMELTING) {
            return original.call(manager, type, container, level);
        }
        int generation = RecipeCacheGeneration.generation();
        if (this.coo$smeltingCheck == null || this.coo$smeltingCheckGeneration != generation) {
            this.coo$smeltingCheck = RecipeManager.createCheck(RecipeType.SMELTING);
            this.coo$smeltingCheckGeneration = generation;
        }
        return this.coo$smeltingCheck.getRecipeFor(container, level);
    }
}
