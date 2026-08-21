package com.misanthropy.collections_of_optimizations.mixin.fancymenu;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.RenderThreadLocal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = {
        "de.keksuccino.fancymenu.util.rendering.RenderScaleUtil",
        "de.keksuccino.fancymenu.util.rendering.RenderTranslationUtil",
        "de.keksuccino.fancymenu.util.rendering.RenderRotationUtil"
}, remap = false)
public abstract class MixinFancyMenuRenderState {

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/ThreadLocal;withInitial(Ljava/util/function/Supplier;)Ljava/lang/ThreadLocal;"
            ),
            require = 0
    )
    private static ThreadLocal<?> coo$pinRenderState(ThreadLocal<?> original) {
        return CoOConfig.fancymenuPinRenderStateToRenderThread
                ? new RenderThreadLocal<>(original)
                : original;
    }
}
