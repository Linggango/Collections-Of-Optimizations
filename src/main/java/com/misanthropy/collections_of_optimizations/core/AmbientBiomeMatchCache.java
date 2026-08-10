package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

public final class AmbientBiomeMatchCache {

    private static final Map<Object, Map<Holder<Biome>, Boolean>> MATCHES = new WeakHashMap<>();

    private AmbientBiomeMatchCache() {
    }

    public static Boolean get(Object conditions, Holder<Biome> biome) {
        Map<Holder<Biome>, Boolean> perBiome = MATCHES.get(conditions);
        return perBiome == null ? null : perBiome.get(biome);
    }

    public static void put(Object conditions, Holder<Biome> biome, boolean matched) {
        MATCHES.computeIfAbsent(conditions, key -> new IdentityHashMap<>()).put(biome, matched);
    }
}
