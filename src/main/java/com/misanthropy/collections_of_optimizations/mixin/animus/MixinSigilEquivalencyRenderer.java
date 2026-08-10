package com.misanthropy.collections_of_optimizations.mixin.animus;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.EquivalencyPreviewCache;
import com.teamdman.animus.client.SigilEquivalencyRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = SigilEquivalencyRenderer.class, remap = false)
public abstract class MixinSigilEquivalencyRenderer {

    @WrapOperation(
            method = "onRenderLevelStage",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/teamdman/animus/client/SigilEquivalencyRenderer;findMatchingBlocksInRadius(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;ILnet/minecraft/core/Direction;)Ljava/util/List;"
            ),
            require = 0
    )
    private static List<BlockPos> coo$cachePreviewFill(Level level,
                                                      BlockPos center,
                                                      Block block,
                                                      int radius,
                                                      Direction face,
                                                      Operation<List<BlockPos>> original) {
        if (!CoOConfig.animusCacheEquivalencyPreview) {
            return original.call(level, center, block, radius, face);
        }
        List<BlockPos> cached = EquivalencyPreviewCache.get(level, center, block, radius, face);
        if (cached != null) {
            return cached;
        }
        List<BlockPos> value = original.call(level, center, block, radius, face);
        EquivalencyPreviewCache.put(level, center, block, radius, face, value);
        return value;
    }
}
