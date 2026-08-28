package com.misanthropy.collections_of_optimizations.core;

import com.github.alexmodguy.alexscaves.server.misc.VoronoiGenerator;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public final class CaveBiomeQuadCache {

    private static final int LIMIT = 4096;

    public static final VoronoiGenerator.VoronoiInfo ABSENT =
            new VoronoiGenerator.VoronoiInfo(0.0D, 0.0D, 0.0D, null, null);

    private static final ThreadLocal<CaveBiomeQuadCache> LOCAL = ThreadLocal.withInitial(CaveBiomeQuadCache::new);

    private static volatile int generation;

    private static boolean configListenerRegistered;

    private final Long2ObjectOpenHashMap<VoronoiGenerator.VoronoiInfo> entries = new Long2ObjectOpenHashMap<>();

    private long seed;
    private int seenGeneration = -1;

    private CaveBiomeQuadCache() {
    }

    public static CaveBiomeQuadCache get() {
        return LOCAL.get();
    }

    public static void invalidate() {
        generation++;
    }

    public static synchronized void armConfigInvalidation() {
        if (!configListenerRegistered) {
            configListenerRegistered = true;
            MinecraftForge.EVENT_BUS.addListener((ModConfigEvent.Reloading event) -> invalidate());
        }
    }

    public VoronoiGenerator.VoronoiInfo lookup(long worldSeed, int x, int z) {
        reset(worldSeed);
        return this.entries.get(key(x, z));
    }

    public void store(long worldSeed, int x, int z, VoronoiGenerator.VoronoiInfo value) {
        reset(worldSeed);
        if (this.entries.size() >= LIMIT) {
            this.entries.clear();
        }
        this.entries.put(key(x, z), value == null ? ABSENT : value);
    }

    private void reset(long worldSeed) {
        int current = generation;
        if (this.seenGeneration != current || this.seed != worldSeed) {
            this.entries.clear();
            this.seenGeneration = current;
            this.seed = worldSeed;
        }
    }

    private static long key(int x, int z) {
        return ((long) x << 32) ^ (z & 0xFFFFFFFFL);
    }
}
