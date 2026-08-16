package com.misanthropy.collections_of_optimizations.mixin.goetydelight;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.VisualEffectLatch;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.v_black_cat.goetydelight.visual.client.EntityVisualEffectRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityVisualEffectRenderDispatcher.class, remap = false)
public abstract class MixinEntityVisualEffectRenderDispatcher {

    @Inject(
            method = "render(Lnet/minecraftforge/client/event/RenderLevelStageEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipIdlePass(RenderLevelStageEvent event, CallbackInfo ci) {
        if (!CoOConfig.goetydelightSkipIdleVisualEffects) {
            return;
        }
        if (VisualEffectLatch.armed()) {
            VisualEffectLatch.beginPass();
        } else {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(
            method = "render(Lnet/minecraftforge/client/event/RenderLevelStageEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/v_black_cat/goetydelight/visual/EntityVisualEffects;isEmpty()Z"
            ),
            require = 0
    )
    private static boolean coo$observeEntity(boolean empty) {
        if (CoOConfig.goetydelightSkipIdleVisualEffects) {
            VisualEffectLatch.observe(empty);
        }
        return empty;
    }

    @Inject(
            method = "render(Lnet/minecraftforge/client/event/RenderLevelStageEvent;)V",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$disarmIfNothingFound(RenderLevelStageEvent event, CallbackInfo ci) {
        if (CoOConfig.goetydelightSkipIdleVisualEffects) {
            VisualEffectLatch.endPass();
        }
    }
}
