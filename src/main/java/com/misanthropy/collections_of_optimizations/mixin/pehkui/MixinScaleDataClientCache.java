package com.misanthropy.collections_of_optimizations.mixin.pehkui;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import virtuoel.pehkui.api.ScaleData;

@Mixin(value = ScaleData.class, remap = false)
public abstract class MixinScaleDataClientCache {

    @ModifyExpressionValue(
            method = "getScale(F)F",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;f_46443_:Z",
                    opcode = Opcodes.GETFIELD
            ),
            require = 0
    )
    private boolean coo$allowClientScaleCache(boolean clientSide) {
        return clientSide && !CoOConfig.pehkuiCacheClientScales;
    }
}
