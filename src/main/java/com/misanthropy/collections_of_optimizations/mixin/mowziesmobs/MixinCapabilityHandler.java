package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.MowzieCapHolder;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CapabilityHandler.class, remap = false)
public abstract class MixinCapabilityHandler {

    @Inject(method = "getCapability", at = @At("HEAD"), cancellable = true, require = 0)
    private static <T> void coo$mowzieCapFromEntity(Entity entity, Capability<T> capability, CallbackInfoReturnable<T> cir) {
        if (!CoOConfig.mowziesmobsFastCapabilityLookup || entity == null || entity.isRemoved()) {
            return;
        }
        if (!(entity instanceof MowzieCapHolder holder)) {
            return;
        }
        int index = coo$slotFor(capability);
        if (index < 0) {
            return;
        }
        Object cached = holder.coo$getMowzieCap(index);
        if (cached == MowzieCapHolder.COO_ABSENT) {
            cir.setReturnValue(null);
        } else if (cached != null) {
            @SuppressWarnings("unchecked")
            T value = (T) cached;
            cir.setReturnValue(value);
        }
    }

    @Inject(method = "getCapability", at = @At("RETURN"), require = 0)
    private static <T> void coo$rememberMowzieCap(Entity entity, Capability<T> capability, CallbackInfoReturnable<T> cir) {
        if (!CoOConfig.mowziesmobsFastCapabilityLookup || entity == null || entity.isRemoved()) {
            return;
        }
        if (!(entity instanceof MowzieCapHolder holder)) {
            return;
        }
        int index = coo$slotFor(capability);
        if (index < 0) {
            return;
        }
        T resolved = cir.getReturnValue();
        holder.coo$setMowzieCap(index, resolved == null ? MowzieCapHolder.COO_ABSENT : resolved);
    }

    private static int coo$slotFor(Capability<?> capability) {
        if (capability == CapabilityHandler.FROZEN_CAPABILITY) {
            return 0;
        }
        if (capability == CapabilityHandler.LIVING_CAPABILITY) {
            return 1;
        }
        if (capability == CapabilityHandler.ABILITY_CAPABILITY) {
            return 2;
        }
        if (capability == CapabilityHandler.PLAYER_CAPABILITY) {
            return 3;
        }
        return -1;
    }
}
