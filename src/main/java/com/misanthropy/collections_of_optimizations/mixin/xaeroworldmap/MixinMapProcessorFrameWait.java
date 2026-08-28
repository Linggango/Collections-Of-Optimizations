package com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xaero.map.MapProcessor;

import java.util.concurrent.locks.LockSupport;

@Mixin(value = MapProcessor.class, remap = false)
public abstract class MixinMapProcessorFrameWait {

    @Shadow
    private long renderStartTime;

    @Shadow
    public abstract boolean actuallySkippingWorldRender();

    @WrapMethod(method = "resetRenderStartTime", require = 0)
    private void coo$idleMapFrameWait(Operation<Void> original) {
        if (!CoOConfig.xaeroworldmapIdleMapFrameWait || !this.actuallySkippingWorldRender()) {
            original.call();
            return;
        }

        long deadline = this.renderStartTime + 1_600_000L;
        long spinTail = CoOConfig.xaeroworldmapMapFrameSpinTail * 1_000L;
        long remaining = deadline - System.nanoTime();
        while (remaining > 0L) {
            if (remaining > spinTail) {
                LockSupport.parkNanos(remaining - spinTail);
            } else {
                Thread.onSpinWait();
            }
            remaining = deadline - System.nanoTime();
        }

        this.renderStartTime = -1L;
    }
}
