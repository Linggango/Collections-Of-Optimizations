package com.misanthropy.collections_of_optimizations.mixin.pehkui;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.TypedScaleModifier;

@Mixin(value = TypedScaleModifier.class, remap = false)
public abstract class MixinTypedScaleModifier {

    @Unique
    private ScaleType coo$type;

    @WrapMethod(method = "getType", require = 0)
    private ScaleType coo$memoType(Operation<ScaleType> original) {
        if (!CoOConfig.pehkuiMemoModifierType) {
            return original.call();
        }

        ScaleType cached = this.coo$type;
        if (cached == null) {
            cached = original.call();
            this.coo$type = cached;
        }
        return cached;
    }
}
