package com.misanthropy.collections_of_optimizations.mixin.cofh;

import cofh.core.client.event.CoreClientEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.TranslucentEntityScan;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CoreClientEvents.class, remap = false)
public abstract class MixinCoreClientEvents {

    @WrapOperation(
            method = "renderTranslucent(Lnet/minecraftforge/client/event/RenderLevelStageEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcofh/lib/client/renderer/entity/ITranslucentRenderer;renderTranslucent"
                            + "(Lcom/mojang/blaze3d/vertex/PoseStack;FLnet/minecraft/client/renderer/LevelRenderer;"
                            + "Lorg/joml/Matrix4f;)V"
            ),
            require = 0
    )
    private static void coo$skipEmptyTranslucentPass(PoseStack pose, float partialTick, LevelRenderer levelRenderer,
                                                     Matrix4f projection, Operation<Void> original) {
        if (CoOConfig.cofhCacheTranslucentRenderers) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientLevel level = minecraft.level;
            if (level != null
                    && TranslucentEntityScan.matches(level, minecraft.getEntityRenderDispatcher()).isEmpty()) {
                return;
            }
        }
        original.call(pose, partialTick, levelRenderer, projection);
    }
}
