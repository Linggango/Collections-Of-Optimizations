package com.misanthropy.collections_of_optimizations.mixin.goety;

import com.Polarice3.Goety.utils.CuriosFinder;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CuriosFinder.class, remap = false)
public abstract class MixinCuriosFinder {

    @WrapMethod(method = "findCurio(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/Item;)Lnet/minecraft/world/item/ItemStack;")
    private static ItemStack coo$fastCurioItemMiss(LivingEntity livingEntity, Item item,
                                                   Operation<ItemStack> original) {
        if (CoOConfig.goetyFastCurioItemMiss
                && livingEntity instanceof Player
                && !CurioPresenceCache.mayHaveEquipped(livingEntity, item)) {
            return ItemStack.EMPTY;
        }
        return original.call(livingEntity, item);
    }
}
