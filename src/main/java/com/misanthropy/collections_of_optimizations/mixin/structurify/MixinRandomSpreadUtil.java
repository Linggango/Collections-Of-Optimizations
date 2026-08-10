package com.misanthropy.collections_of_optimizations.mixin.structurify;

import com.faboslav.structurify.common.config.data.StructureSetData;
import com.faboslav.structurify.common.util.RandomSpreadUtil;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.StructurifyConfigMirror;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RandomSpreadUtil.class, remap = false)
public abstract class MixinRandomSpreadUtil {

    @Inject(
            method = "getStructureSetData",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$fastStructureSetLookup(String structureSetId, CallbackInfoReturnable<StructureSetData> cir) {
        if (!CoOConfig.structurifyFastStructureSetLookup) {
            return;
        }
        cir.setReturnValue(StructurifyConfigMirror.structureSetData(structureSetId));
    }
}
