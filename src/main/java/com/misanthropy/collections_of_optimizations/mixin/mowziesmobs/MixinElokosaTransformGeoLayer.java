package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ElokosaTransformGeoLayer;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

@Mixin(value = ElokosaTransformGeoLayer.class, remap = false)
public abstract class MixinElokosaTransformGeoLayer {

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/bobmowzie/mowziesmobs/server/entity/elokosa/EntityElokosa;Lsoftware/bernie/geckolib/cache/object/BakedGeoModel;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/VertexConsumer;FII)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipBlankTransformPass(
            PoseStack poseStack,
            EntityElokosa animatable,
            BakedGeoModel bakedModel,
            RenderType renderType,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            float partialTick,
            int packedLight,
            int packedOverlay,
            CallbackInfo ci) {
        if (!CoOConfig.mowziesmobsSkipBlankElokosaTransform) {
            return;
        }
        ElokosaTransformGeoLayer self = (ElokosaTransformGeoLayer) (Object) this;
        GeoModel<EntityElokosa> model = self.getRenderer().getGeoModel();
        if (!(model instanceof MowzieGeoModel<EntityElokosa> mowzieModel) || !mowzieModel.isInitialized()) {
            ci.cancel();
            return;
        }
        if (-mowzieModel.getControllerValue("transformTextureController") <= 0.0F) {
            ci.cancel();
        }
    }
}
