package com.misanthropy.collections_of_optimizations.mixin.oculus;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ShadowPass;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignRenderer.class)
public abstract class MixinSignRenderer {

    @Inject(method = "renderSignText", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipSignTextInShadowPass(CallbackInfo ci) {
        if (CoOConfig.oculusSkipSignTextInShadowPass && ShadowPass.active()) {
            ci.cancel();
        }
    }
}
