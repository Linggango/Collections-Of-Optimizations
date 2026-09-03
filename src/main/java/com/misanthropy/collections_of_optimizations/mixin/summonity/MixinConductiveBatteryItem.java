package com.misanthropy.collections_of_optimizations.mixin.summonity;

import com.fevzi.summonity.item.ConductiveBatteryItem;
import com.fevzi.summonity.registry.ModItems;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ConductiveBatteryItem.class, remap = false)
public abstract class MixinConductiveBatteryItem {

    @WrapMethod(method = "isEquipped(Lnet/minecraft/world/entity/LivingEntity;)Z")
    private static boolean coo$fastCurioMiss(LivingEntity entity, Operation<Boolean> original) {
        return CoOConfig.summonityFastCurioMiss && !CurioPresenceCache.mayHaveEquipped(entity, ModItems.CONDUCTIVE_BATTERY.get())
                ? false
                : original.call(entity);
    }
}
