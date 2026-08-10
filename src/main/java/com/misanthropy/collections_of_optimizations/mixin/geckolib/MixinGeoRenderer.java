package com.misanthropy.collections_of_optimizations.mixin.geckolib;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.GeckoScratch;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.renderer.GeoRenderer;

@Mixin(value = GeoRenderer.class, remap = false)
public interface MixinGeoRenderer {

    /**
     * @author Misanthropy
     * @reason Removes one Vector4f allocation per model vertex per frame
     */
    @Overwrite(remap = false)
    default void createVerticesOfQuad(GeoQuad quad, Matrix4f poseState, Vector3f normal, VertexConsumer buffer,
                                      int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        boolean reusable = CoOConfig.geckolibReuseRenderVectors && Minecraft.getInstance().isSameThread();
        Vector3f transformed = GeckoScratch.position(reusable);

        for (GeoVertex vertex : quad.vertices()) {
            vertex.position().mulPosition(poseState, transformed);
            buffer.vertex(transformed.x(), transformed.y(), transformed.z(), red, green, blue, alpha,
                    vertex.texU(), vertex.texV(), packedOverlay, packedLight, normal.x(), normal.y(), normal.z());
        }
    }
}
