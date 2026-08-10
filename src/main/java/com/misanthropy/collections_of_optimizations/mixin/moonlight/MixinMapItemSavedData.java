package com.misanthropy.collections_of_optimizations.mixin.moonlight;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mehvahdjukaar.moonlight.api.map.ExpandedMapData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapItemSavedData.class)
public abstract class MixinMapItemSavedData {

    @Inject(
            method = "*(Lnet/minecraft/world/level/BlockGetter;IILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V",
            at = @At(value = "NEW", target = "java/util/ArrayList", ordinal = 0, remap = false),
            cancellable = true,
            require = 0,
            expect = 0,
            remap = false
    )
    private void coo$skipEmptyMapMarkerScan(BlockGetter world, int x, int z, CallbackInfo theirCallback, CallbackInfo ci) {
        if (CoOConfig.moonlightSkipEmptyMapMarkerScan
                && ((ExpandedMapData) this).getCustomMarkers().isEmpty()) {
            ci.cancel();
        }
    }
}
