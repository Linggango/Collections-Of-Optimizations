package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.client.render.CuriosLayer;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer {

    @Inject(method = "addLayer", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$rejectCuriosLayerOnNonPlayers(RenderLayer<?, ?> layer, CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.curiosSkipNonPlayerRenderLayer
                && layer instanceof CuriosLayer
                && !((Object) this instanceof PlayerRenderer)) {
            cir.setReturnValue(false);
        }
    }
}
