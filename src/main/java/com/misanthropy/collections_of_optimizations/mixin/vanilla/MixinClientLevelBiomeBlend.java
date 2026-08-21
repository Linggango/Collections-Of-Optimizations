package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BiomeBlendCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevelBiomeBlend {

    @WrapMethod(method = "calculateBlockTint(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/ColorResolver;)I", require = 0)
    private int coo$fastBiomeBlend(BlockPos pos, ColorResolver resolver, Operation<Integer> original) {
        if (!CoOConfig.vanillaFastBiomeBlend) {
            return original.call(pos, resolver);
        }

        int radius = Minecraft.getInstance().options.biomeBlendRadius().get();
        if (radius < 1 || radius > BiomeBlendCache.MAX_RADIUS) {
            return original.call(pos, resolver);
        }

        return BiomeBlendCache.blend((ClientLevel) (Object) this, resolver, pos, radius);
    }

    @Inject(method = "onChunkLoaded(Lnet/minecraft/world/level/ChunkPos;)V", at = @At("HEAD"), require = 0)
    private void coo$dropBlendCacheOnChunkLoad(ChunkPos pos, CallbackInfo ci) {
        BiomeBlendCache.invalidate();
    }

    @Inject(method = "clearTintCaches()V", at = @At("HEAD"), require = 0)
    private void coo$dropBlendCacheOnClear(CallbackInfo ci) {
        BiomeBlendCache.invalidate();
    }
}
