package com.misanthropy.collections_of_optimizations.mixin.pehkui;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleEventCallback;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.util.ScaleUtils;

import java.util.Collection;

@Mixin(value = ScaleUtils.class, remap = false)
public abstract class MixinScaleUtils {

    @Inject(method = "tickScale", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$leanTickScale(ScaleData data, CallbackInfo ci) {
        if (!CoOConfig.pehkuiLeanScaleTick) {
            return;
        }

        ScaleType type = data.getScaleType();

        Collection<ScaleEventCallback> pre = type.getPreTickEvent();
        if (!pre.isEmpty()) {
            for (ScaleEventCallback callback : pre) {
                callback.onEvent(data);
            }
        }

        data.tick();

        Collection<ScaleEventCallback> post = type.getPostTickEvent();
        if (!post.isEmpty()) {
            for (ScaleEventCallback callback : post) {
                callback.onEvent(data);
            }
        }

        ci.cancel();
    }
}
