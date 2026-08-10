package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BlockStateKeyCache {

    private static final int LIMIT = 4096;

    private static final Map<BlockState, String> CACHE = new ConcurrentHashMap<>();

    private BlockStateKeyCache() {
    }

    public static String lookup(BlockState state) {
        if (state == null) {
            return null;
        }
        return CACHE.get(state);
    }

    public static void store(BlockState state, String key) {
        if (state == null || key == null) {
            return;
        }
        if (CACHE.size() >= LIMIT) {
            CACHE.clear();
        }
        CACHE.put(state, key);
    }
}
