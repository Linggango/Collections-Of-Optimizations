package com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ClientTickStamp;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.MapProcessor;

@Mixin(value = MapProcessor.class, remap = false)
public abstract class MixinMapProcessorTickThrottle {

    @Unique
    private int coo$lastProcessedStamp = Integer.MIN_VALUE;

    @Inject(method = "onRenderProcess", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$throttleToClientTick(Minecraft minecraft, CallbackInfo ci) {
        int interval = CoOConfig.xaeroworldmapRenderProcessInterval;
        if (interval <= 0) {
            return;
        }

        if (minecraft.screen != null) {
            return;
        }

        int stamp = ClientTickStamp.currentOnRenderThread();
        if (stamp < 0) {
            return;
        }

        if (stamp - this.coo$lastProcessedStamp < interval) {
            ci.cancel();
            return;
        }
        this.coo$lastProcessedStamp = stamp;
    }
}
