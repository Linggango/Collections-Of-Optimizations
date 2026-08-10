package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;

import java.lang.ref.WeakReference;

public final class StructurifyHeightCache {

    public static final int ABSENT = Integer.MIN_VALUE;

    private static final int MAX_ENTRIES = 16384;

    private static final int COORD_LIMIT = 1 << 26;

    private static final long COORD_MASK = 0x7FFFFFFL;

    private static final long NO_KEY = Long.MIN_VALUE;

    private static final ThreadLocal<StructurifyHeightCache> LOCAL =
            ThreadLocal.withInitial(StructurifyHeightCache::new);

    private final Long2IntOpenHashMap heights = new Long2IntOpenHashMap(1024);

    private WeakReference<ChunkGenerator> generator;
    private WeakReference<LevelHeightAccessor> heightAccessor;
    private WeakReference<RandomState> randomState;

    private StructurifyHeightCache() {
        this.heights.defaultReturnValue(ABSENT);
    }

    public static int freeHeight(ChunkGenerator generator,
                                 int x,
                                 int z,
                                 Heightmap.Types heightmapType,
                                 LevelHeightAccessor heightAccessor,
                                 RandomState randomState) {
        long key = key(x, z, heightmapType);
        if (key == NO_KEY) {
            return ABSENT;
        }
        StructurifyHeightCache cache = LOCAL.get();
        if (!cache.matches(generator, heightAccessor, randomState)) {
            cache.rebind(generator, heightAccessor, randomState);
            return ABSENT;
        }
        return cache.heights.get(key);
    }

    public static void putFreeHeight(ChunkGenerator generator,
                                     int x,
                                     int z,
                                     Heightmap.Types heightmapType,
                                     LevelHeightAccessor heightAccessor,
                                     RandomState randomState,
                                     int freeHeight) {
        if (freeHeight == ABSENT) {
            return;
        }
        long key = key(x, z, heightmapType);
        if (key == NO_KEY) {
            return;
        }
        StructurifyHeightCache cache = LOCAL.get();
        if (!cache.matches(generator, heightAccessor, randomState)) {
            cache.rebind(generator, heightAccessor, randomState);
        } else if (cache.heights.size() >= MAX_ENTRIES) {
            cache.heights.clear();
        }
        cache.heights.put(key, freeHeight);
    }

    private boolean matches(ChunkGenerator generator,
                            LevelHeightAccessor heightAccessor,
                            RandomState randomState) {
        return this.generator != null
                && this.generator.get() == generator
                && this.heightAccessor.get() == heightAccessor
                && this.randomState.get() == randomState;
    }

    private void rebind(ChunkGenerator generator,
                        LevelHeightAccessor heightAccessor,
                        RandomState randomState) {
        this.heights.clear();
        this.generator = new WeakReference<>(generator);
        this.heightAccessor = new WeakReference<>(heightAccessor);
        this.randomState = new WeakReference<>(randomState);
    }

    private static long key(int x, int z, Heightmap.Types heightmapType) {
        if (x <= -COORD_LIMIT || x >= COORD_LIMIT || z <= -COORD_LIMIT || z >= COORD_LIMIT) {
            return NO_KEY;
        }
        int type = heightmapType.ordinal();
        if (type < 0 || type > 7) {
            return NO_KEY;
        }
        return ((x & COORD_MASK) << 30) | ((z & COORD_MASK) << 3) | type;
    }
}
