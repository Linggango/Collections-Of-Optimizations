package com.misanthropy.collections_of_optimizations.mixin.fancymenu;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "de.keksuccino.fancymenu.util.rendering.RenderScaleUtil", remap = false)
public abstract class MixinRenderScaleUtil {

    @Shadow
    private static ThreadLocal<Float> ACTIVE_RENDER_SCALE_FANCYMENU;

    @Inject(
            method = "setActiveRenderScale_FancyMenu(F)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipRedundantScaleWrite(float renderScale, CallbackInfo ci) {
        if (CoOConfig.fancymenuSkipRedundantScaleWrites
                && ACTIVE_RENDER_SCALE_FANCYMENU.get() == renderScale) {
            ci.cancel();
        }
    }
}
