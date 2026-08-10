package com.misanthropy.collections_of_optimizations.mixin.bloodmagic;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.RecipeCacheGeneration;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import wayoftime.bloodmagic.impl.BloodMagicRecipeRegistrar;

import java.util.List;

@Mixin(value = BloodMagicRecipeRegistrar.class, remap = false)
public abstract class MixinBloodMagicRecipeRegistrar {

    @Unique
    private List<?> coo$cachedArcRecipes;

    @Unique
    private int coo$cachedArcGeneration = -1;

    @Unique
    private RecipeManager coo$cachedArcManager;

    @WrapOperation(
            method = "getARC",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/RecipeManager;getAllRecipesFor(Lnet/minecraft/world/item/crafting/RecipeType;)Ljava/util/List;",
                    remap = true
            ),
            require = 0
    )
    private List<?> coo$cacheArcRecipeList(RecipeManager manager, RecipeType<?> type, Operation<List<?>> original) {
        if (!CoOConfig.bloodmagicCacheArcRecipeList) {
            return original.call(manager, type);
        }
        int generation = RecipeCacheGeneration.generation();
        if (this.coo$cachedArcRecipes == null
                || this.coo$cachedArcGeneration != generation
                || this.coo$cachedArcManager != manager) {
            this.coo$cachedArcRecipes = original.call(manager, type);
            this.coo$cachedArcGeneration = generation;
            this.coo$cachedArcManager = manager;
        }
        return this.coo$cachedArcRecipes;
    }
}
