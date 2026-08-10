package com.misanthropy.collections_of_optimizations.mixin.zeta;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.misanthropy.collections_of_optimizations.core.ZetaWrapperMemo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.violetmoon.zetaimplforge.event.ForgeEventsRemapper;

import java.util.function.Function;

@Mixin(value = ForgeEventsRemapper.class, remap = false)
public abstract class MixinForgeEventsRemapper {

    @ModifyExpressionValue(
            method = "remapMethod",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            ),
            require = 0
    )
    private Object coo$shareEventWrappers(Object constructor) {
        return constructor instanceof Function<?, ?> function ? ZetaWrapperMemo.memoise(function) : constructor;
    }
}
