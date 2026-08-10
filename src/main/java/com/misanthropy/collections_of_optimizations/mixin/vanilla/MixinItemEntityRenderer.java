package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer {

    @ModifyReturnValue(method = "getRenderAmount", at = @At("RETURN"), require = 0)
    private int coo$capRenderAmount(int original) {
        int cap = CoOConfig.itemEntityRenderCap;
        return cap > 0 ? Math.min(original, cap) : original;
    }
}
