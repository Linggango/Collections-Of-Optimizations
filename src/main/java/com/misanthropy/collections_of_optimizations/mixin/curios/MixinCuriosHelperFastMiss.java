package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.common.CuriosHelper;

import java.util.Optional;

@Mixin(value = CuriosHelper.class, remap = false)
public abstract class MixinCuriosHelperFastMiss {

    @Inject(
            method = "findEquippedCurio(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$fastEquippedMiss(Item item, LivingEntity livingEntity,
                                      CallbackInfoReturnable<Optional<ImmutableTriple<String, Integer, net.minecraft.world.item.ItemStack>>> cir) {
        if (!CoOConfig.curiosFastEquippedItemMiss) {
            return;
        }
        if (!CurioPresenceCache.mayHaveEquipped(livingEntity, item)) {
            cir.setReturnValue(Optional.empty());
        }
    }

}
