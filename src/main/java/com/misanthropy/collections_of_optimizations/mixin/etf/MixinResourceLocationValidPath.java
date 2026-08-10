package com.misanthropy.collections_of_optimizations.mixin.etf;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ResourceLocation.class, priority = 1500)
public abstract class MixinResourceLocationValidPath {

    @Inject(
            method = "isValidPath",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$fastValidPath(String path, CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.etfFastValidPath || path == null) {
            return;
        }
        for (int i = 0, n = path.length(); i < n; i++) {
            if (!ResourceLocation.validPathChar(path.charAt(i))) {
                return;
            }
        }
        cir.setReturnValue(true);
    }
}
