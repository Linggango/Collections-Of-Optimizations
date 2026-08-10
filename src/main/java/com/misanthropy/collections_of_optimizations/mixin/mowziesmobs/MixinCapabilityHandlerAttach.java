package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CapabilityHandler.class, remap = false)
public abstract class MixinCapabilityHandlerAttach {

    @Inject(method = "attachEntityCapability", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipDuplicateAttach(AttachCapabilitiesEvent<Entity> event, CallbackInfo ci) {
        if (CoOConfig.mowziesmobsDedupeCapabilityAttach) {
            ci.cancel();
        }
    }
}
