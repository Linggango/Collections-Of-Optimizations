package com.misanthropy.collections_of_optimizations.mixin.cnc;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.imasillylittleguy.cnc.procedures.SasquatchWoodKnockProcedure", remap = false)
public abstract class MixinCncSasquatchWoodKnock {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$serverSideOnly(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.cncSkipClientWoodKnockScan || event == null) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity != null && entity.level().isClientSide()) {
            ci.cancel();
        }
    }
}
