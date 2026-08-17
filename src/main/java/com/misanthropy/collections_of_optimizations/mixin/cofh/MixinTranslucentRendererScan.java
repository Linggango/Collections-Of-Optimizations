package com.misanthropy.collections_of_optimizations.mixin.cofh;

import cofh.lib.client.renderer.entity.ITranslucentRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.TranslucentEntityScan;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ITranslucentRenderer.class, remap = false)
public interface MixinTranslucentRendererScan {

    @WrapOperation(
            method = "renderTranslucent(Lcom/mojang/blaze3d/vertex/PoseStack;FLnet/minecraft/client/renderer/LevelRenderer;Lorg/joml/Matrix4f;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;entitiesForRendering()Ljava/lang/Iterable;",
                    remap = true
            ),
            require = 0
    )
    public static Iterable<Entity> coo$onlyWalkTranslucentEntities(ClientLevel level,
                                                                    Operation<Iterable<Entity>> original) {
        if (!CoOConfig.cofhCacheTranslucentRenderers) {
            return original.call(level);
        }
        return TranslucentEntityScan.matches(level, Minecraft.getInstance().getEntityRenderDispatcher());
    }
}
