package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public final class EquivalencyPreviewCache {

    private static Level level;
    private static BlockPos center;
    private static Block block;
    private static int radius;
    private static Direction face;
    private static long gameTime = Long.MIN_VALUE;
    private static List<BlockPos> result;

    private EquivalencyPreviewCache() {
    }

    @Nullable
    public static List<BlockPos> get(Level level, BlockPos center, Block block, int radius, Direction face) {
        if (result == null
                || EquivalencyPreviewCache.level != level
                || EquivalencyPreviewCache.radius != radius
                || EquivalencyPreviewCache.face != face
                || EquivalencyPreviewCache.block != block
                || !EquivalencyPreviewCache.center.equals(center)
                || EquivalencyPreviewCache.gameTime != level.getGameTime()) {
            return null;
        }
        return result;
    }

    public static void put(Level level, BlockPos center, Block block, int radius, Direction face, List<BlockPos> value) {
        EquivalencyPreviewCache.level = level;
        EquivalencyPreviewCache.center = center.immutable();
        EquivalencyPreviewCache.block = block;
        EquivalencyPreviewCache.radius = radius;
        EquivalencyPreviewCache.face = face;
        EquivalencyPreviewCache.gameTime = level.getGameTime();
        EquivalencyPreviewCache.result = value;
    }
}
