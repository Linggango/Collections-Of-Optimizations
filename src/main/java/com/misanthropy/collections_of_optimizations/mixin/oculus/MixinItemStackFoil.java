package com.misanthropy.collections_of_optimizations.mixin.oculus;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ShadowPass;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinItemStackFoil {

    @Inject(method = "hasFoil", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipGlintInShadowPass(CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.oculusSkipGlintInShadowPass && ShadowPass.active()) {
            cir.setReturnValue(Boolean.FALSE);
        }
    }
}
