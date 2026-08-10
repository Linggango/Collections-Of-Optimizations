package com.misanthropy.collections_of_optimizations.mixin.fancymenu;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Pseudo
@Mixin(targets = "de.keksuccino.fancymenu.customization.global.SeamlessWorldLoadingHandler", remap = false)
public abstract class MixinSeamlessWorldLoadingHandler {

    @ModifyConstant(
            method = "captureFrameIfNeeded",
            constant = @Constant(longValue = 1000L),
            require = 0
    )
    private static long coo$stretchCaptureInterval(long stock) {
        return CoOConfig.fancymenuSeamlessCaptureInterval * 1000L;
    }
}
