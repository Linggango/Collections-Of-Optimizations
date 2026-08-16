package com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "xaero.map.MapLimiter", remap = false)
public abstract class MixinMapLimiter {

    @Unique
    private long coo$lastVramPoll;

    @Inject(method = "updateAvailableVRAM", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$throttleVramPoll(CallbackInfo ci) {
        int interval = CoOConfig.xaeroworldmapVramPollInterval;
        if (interval <= 0) {
            return;
        }
        long now = System.nanoTime() / 1_000_000L;
        if (now - this.coo$lastVramPoll < interval) {
            ci.cancel();
            return;
        }
        this.coo$lastVramPoll = now;
    }
}
