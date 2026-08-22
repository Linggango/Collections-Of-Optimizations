package com.misanthropy.collections_of_optimizations.mixin.distanthorizons;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.seibel.distanthorizons.common.wrappers.block.AbstractDhTintGetter_forge;
import com.seibel.distanthorizons.common.wrappers.block.BiomeWrapper_forge;
import com.seibel.distanthorizons.core.api.internal.SharedApi;
import com.seibel.distanthorizons.core.world.AbstractDhWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SharedApi.class, remap = false)
public abstract class MixinDhSharedApi {

    @Inject(
            method = "setDhWorld",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$dropStaleBiomeCaches(AbstractDhWorld newWorld, CallbackInfo ci) {
        if (newWorld != null || !CoOConfig.distanthorizonsClearBiomeCachesOnUnload) {
            return;
        }
        BiomeWrapper_forge.WRAPPER_BY_BIOME.clear();
        BiomeWrapper_forge.WRAPPER_BY_RESOURCE_LOCATION.clear();
        DhTintGetterBiomeHolderAccessor.coo$biomeByResourceString().clear();
        DhBlockBiomePairAccessor.coo$pairCache().clear();
        AbstractDhTintGetter_forge.clear();
    }
}
