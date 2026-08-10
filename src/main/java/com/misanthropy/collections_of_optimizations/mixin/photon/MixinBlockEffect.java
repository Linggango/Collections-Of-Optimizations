package com.misanthropy.collections_of_optimizations.mixin.photon;

import com.lowdragmc.photon.client.fx.BlockEffect;
import com.lowdragmc.photon.client.gameobject.IFXObject;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = BlockEffect.class, remap = false)
public abstract class MixinBlockEffect {

    @Shadow
    @Final
    public BlockPos pos;

    @Inject(method = "updateFXObjectTick", at = @At("TAIL"), require = 0)
    private void coo$dropEmptyCacheEntry(IFXObject fxObject, CallbackInfo ci) {
        if (!CoOConfig.photonDropEmptyEffectCacheEntries) {
            return;
        }
        List<BlockEffect> effects = BlockEffect.CACHE.get(this.pos);
        if (effects != null && effects.isEmpty()) {
            BlockEffect.CACHE.remove(this.pos);
        }
    }
}
