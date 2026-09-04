package com.misanthropy.collections_of_optimizations.mixin.crittersandcompanions;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.bonsaistudi0s.crittersandcompanions.client.renderer.SilkLeashRenderer;
import io.github.bonsaistudi0s.crittersandcompanions.common.extension.ISilkLeashState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SilkLeashRenderer.class, remap = false)
public abstract class MixinCacSilkLeashRenderer {

    @Inject(
            method = "renderSilkLeash(Lnet/minecraft/world/entity/Entity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipEmptySilkLeash(Entity entity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        if (!CoOConfig.crittersandcompanionsSkipEmptyLeashRender) {
            return;
        }
        if (!(entity instanceof ISilkLeashState state) || state.getLeashedByEntities().isEmpty()) {
            ci.cancel();
        }
    }
}
