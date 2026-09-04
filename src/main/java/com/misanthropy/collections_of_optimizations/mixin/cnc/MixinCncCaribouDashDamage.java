package com.misanthropy.collections_of_optimizations.mixin.cnc;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CncTickState;
import com.misanthropy.collections_of_optimizations.core.ModEntityFilter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.imasillylittleguy.cnc.procedures.CaribouDashDamageProcedure", remap = false)
public abstract class MixinCncCaribouDashDamage {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipWhenNoCaribouAround(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.cncGateCaribouDashScan || event == null) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity == null) {
            return;
        }
        if (ModEntityFilter.CNC_CARIBOU.matches(entity)) {
            CncTickState.markCaribouTick(entity);
            return;
        }
        if (!CncTickState.caribouTickedRecently(entity)) {
            ci.cancel();
        }
    }
}
