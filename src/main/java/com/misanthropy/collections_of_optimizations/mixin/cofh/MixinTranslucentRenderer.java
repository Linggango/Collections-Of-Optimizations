package com.misanthropy.collections_of_optimizations.mixin.cofh;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.TranslucentRendererCache;
import cofh.lib.client.renderer.entity.ITranslucentRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ITranslucentRenderer.class, remap = false)
public interface MixinTranslucentRenderer {

    @WrapOperation(
            method = "renderTranslucent(Lcom/mojang/blaze3d/vertex/PoseStack;FLnet/minecraft/client/renderer/LevelRenderer;Lorg/joml/Matrix4f;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;",
                    remap = true
            ),
            require = 0
    )
    public static EntityRenderer coo$skipKnownOpaqueEntities(EntityRenderDispatcher dispatcher, Entity entity,
                                                              Operation<EntityRenderer> original) {
        if (!CoOConfig.cofhCacheTranslucentRenderers || entity == null) {
            return original.call(dispatcher, entity);
        }
        Class<?> entityClass = entity.getClass();
        Boolean known = TranslucentRendererCache.known(entityClass);
        if (known == Boolean.FALSE) {
            return null;
        }
        EntityRenderer renderer = original.call(dispatcher, entity);
        if (known == null) {
            TranslucentRendererCache.record(entityClass, renderer);
        }
        return renderer;
    }
}
