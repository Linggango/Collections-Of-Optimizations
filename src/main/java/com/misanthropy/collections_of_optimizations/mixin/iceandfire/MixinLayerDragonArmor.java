package com.misanthropy.collections_of_optimizations.mixin.iceandfire;

import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerDragonArmor;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LayerDragonArmor.class, remap = false)
public abstract class MixinLayerDragonArmor {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipUnarmouredDragonLayer(
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
        if (!CoOConfig.iceandfireSkipEmptyArmorLayer) {
            return;
        }
        if (dragon.getArmorOrdinal(dragon.getItemBySlot(EquipmentSlot.HEAD)) == 0
                && dragon.getArmorOrdinal(dragon.getItemBySlot(EquipmentSlot.CHEST)) == 0
                && dragon.getArmorOrdinal(dragon.getItemBySlot(EquipmentSlot.LEGS)) == 0
                && dragon.getArmorOrdinal(dragon.getItemBySlot(EquipmentSlot.FEET)) == 0) {
            ci.cancel();
        }
    }
}
