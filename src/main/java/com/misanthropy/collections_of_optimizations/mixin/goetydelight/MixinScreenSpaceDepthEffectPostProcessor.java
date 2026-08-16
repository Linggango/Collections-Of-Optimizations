package com.misanthropy.collections_of_optimizations.mixin.goetydelight;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.VisualEffectLatch;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.v_black_cat.goetydelight.visual.client.ScreenSpaceDepthEffectPostProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScreenSpaceDepthEffectPostProcessor.class, remap = false)
public abstract class MixinScreenSpaceDepthEffectPostProcessor {

    @Inject(
            method = "process(Lnet/minecraftforge/client/event/RenderLevelStageEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipIdleDepthPass(RenderLevelStageEvent event, CallbackInfo ci) {
        if (CoOConfig.goetydelightSkipIdleVisualEffects && !VisualEffectLatch.armed()) {
            ci.cancel();
        }
    }
}
