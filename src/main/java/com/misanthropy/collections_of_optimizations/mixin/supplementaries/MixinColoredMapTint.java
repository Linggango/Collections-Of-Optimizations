package com.misanthropy.collections_of_optimizations.mixin.supplementaries;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.MapTintCache;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.Optional;

@Mixin(targets = "net.mehvahdjukaar.supplementaries.common.misc.ColoredMapHandler$ColorData", remap = false)
public abstract class MixinColoredMapTint {

    @WrapOperation(
            method = "markColored",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/mehvahdjukaar/supplementaries/common/misc/ColoredMapHandler;getCustomColor(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;"
            ),
            require = 0
    )
    private Block coo$memoCustomColor(Block block, Operation<Block> original) {
        if (!CoOConfig.supplementariesMemoMapTintLookup) {
            return original.call(block);
        }
        Map<Block, Optional<Block>> cache = MapTintCache.cache();
        Optional<Block> cached = cache.get(block);
        if (cached == null) {
            cached = Optional.ofNullable(original.call(block));
            cache.put(block, cached);
        }
        return cached.orElse(null);
    }
}
