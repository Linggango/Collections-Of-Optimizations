package com.misanthropy.collections_of_optimizations.mixin.punchy;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(MultiPackResourceManager.class)
public abstract class MixinMultiPackResourceManager {

    @Unique
    private static final int coo$MISS_LIMIT = 16384;

    @Unique
    private Set<ResourceLocation> coo$knownMisses;

    @Inject(
            method = "<init>",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$initMissCache(PackType packType, List<PackResources> packs, CallbackInfo ci) {
        this.coo$knownMisses = ConcurrentHashMap.newKeySet();
    }

    @Inject(
            method = "getResourceStack",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$serveKnownMiss(ResourceLocation location, CallbackInfoReturnable<List<Resource>> cir) {
        Set<ResourceLocation> misses = this.coo$knownMisses;
        if (!CoOConfig.punchyCacheResourceStackMisses || misses == null) {
            return;
        }
        if (misses.contains(location)) {
            cir.setReturnValue(List.of());
        }
    }

    @Inject(
            method = "getResourceStack",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$recordMiss(ResourceLocation location, CallbackInfoReturnable<List<Resource>> cir) {
        Set<ResourceLocation> misses = this.coo$knownMisses;
        if (!CoOConfig.punchyCacheResourceStackMisses || misses == null) {
            return;
        }
        List<Resource> result = cir.getReturnValue();
        if (result != null && result.isEmpty() && misses.size() < coo$MISS_LIMIT) {
            misses.add(location);
        }
    }
}
