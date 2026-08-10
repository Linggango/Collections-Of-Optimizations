package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AdvancedModelRenderer.ModelBox.class, remap = false)
public abstract class MixinAdvancedModelBox {

    @Unique
    private Vector3f coo$scratchNormal = new Vector3f();

    @Unique
    private Vector4f coo$scratchPosition = new Vector4f();

    @Redirect(
            method = "render",
            at = @At(value = "NEW", target = "(Lorg/joml/Vector3fc;)Lorg/joml/Vector3f;"),
            require = 0
    )
    private Vector3f coo$reuseNormalVector(Vector3fc source) {
        if (!CoOConfig.mowziesmobsLeanModelBoxVectors) {
            return new Vector3f(source);
        }
        return this.coo$scratchNormal.set(source);
    }

    @Redirect(
            method = "render",
            at = @At(value = "NEW", target = "(FFFF)Lorg/joml/Vector4f;"),
            require = 0
    )
    private Vector4f coo$reusePositionVector(float x, float y, float z, float w) {
        if (!CoOConfig.mowziesmobsLeanModelBoxVectors) {
            return new Vector4f(x, y, z, w);
        }
        return this.coo$scratchPosition.set(x, y, z, w);
    }
}
