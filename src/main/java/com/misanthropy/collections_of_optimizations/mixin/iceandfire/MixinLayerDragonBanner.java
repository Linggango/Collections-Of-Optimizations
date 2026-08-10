package com.misanthropy.collections_of_optimizations.mixin.iceandfire;

import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerDragonBanner;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BannerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LayerDragonBanner.class, remap = false)
public abstract class MixinLayerDragonBanner {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipBannerlessDragon(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            EntityDragonBase dragon,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            CallbackInfo ci) {
        if (!CoOConfig.iceandfireSkipEmptyDragonLayers) {
            return;
        }
        if (!(dragon.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof BannerItem)) {
            ci.cancel();
        }
    }
}
