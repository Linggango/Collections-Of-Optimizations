package com.misanthropy.collections_of_optimizations.mixin.enigmaticdice;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.jrdemiurge.enigmaticdice.item.ModItems;
import net.jrdemiurge.enigmaticdice.item.custom.MoaiCharm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MoaiCharm.class, remap = false)
public abstract class MixinMoaiCharm {

    @WrapMethod(method = "isWearingMoaiCharm(Lnet/minecraft/world/entity/LivingEntity;)Z")
    private static boolean coo$fastMoaiCharmMiss(LivingEntity livingEntity, Operation<Boolean> original) {
        if (CoOConfig.enigmaticdiceFastCurioMiss
                && !CurioPresenceCache.mayHaveEquipped(livingEntity, ModItems.MOAI_CHARM.get())) {
            return false;
        }
        return original.call(livingEntity);
    }
}
