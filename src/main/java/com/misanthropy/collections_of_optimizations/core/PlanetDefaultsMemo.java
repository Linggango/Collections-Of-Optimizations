package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PlanetDefaultsMemo {

    private static final Map<ResourceKey<Level>, Float> GRAVITY = new ConcurrentHashMap<>();

    private static final Map<ResourceKey<Level>, Short> TEMPERATURE = new ConcurrentHashMap<>();

    private PlanetDefaultsMemo() {
    }

    public static Float lookupGravity(ResourceKey<Level> dimension) {
        return dimension == null ? null : GRAVITY.get(dimension);
    }

    public static void storeGravity(ResourceKey<Level> dimension, float gravity) {
        if (dimension != null) {
            GRAVITY.put(dimension, gravity);
        }
    }

    public static Short lookupTemperature(ResourceKey<Level> dimension) {
        return dimension == null ? null : TEMPERATURE.get(dimension);
    }

    public static void storeTemperature(ResourceKey<Level> dimension, short temperature) {
        if (dimension != null) {
            TEMPERATURE.put(dimension, temperature);
        }
    }

    public static void invalidate() {
        GRAVITY.clear();
        TEMPERATURE.clear();
    }
}
