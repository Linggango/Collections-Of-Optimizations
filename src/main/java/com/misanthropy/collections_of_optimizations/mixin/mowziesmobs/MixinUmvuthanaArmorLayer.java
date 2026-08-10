package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.client.render.entity.layer.UmvuthanaArmorLayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.cache.object.GeoBone;

@Mixin(value = UmvuthanaArmorLayer.class, remap = false)
public abstract class MixinUmvuthanaArmorLayer {

    @Inject(
            method = "renderForBone(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/bobmowzie/mowziesmobs/server/entity/umvuthana/EntityUmvuthana;Lsoftware/bernie/geckolib/cache/object/GeoBone;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/VertexConsumer;FII)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipUnrelatedBones(
            PoseStack poseStack,
            EntityUmvuthana animatable,
            GeoBone bone,
            RenderType renderType,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            float partialTick,
            int packedLight,
            int packedOverlay,
            CallbackInfo ci) {
        if (!CoOConfig.mowziesmobsLeanLayerBoneScan) {
            return;
        }
        String name = bone.getName();
        if (!"maskTwitcher".equals(name) && !"maskHand".equals(name)) {
            ci.cancel();
        }
    }
}
