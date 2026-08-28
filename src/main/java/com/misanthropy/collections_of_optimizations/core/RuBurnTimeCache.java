package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.item.Item;

import java.util.concurrent.ConcurrentHashMap;

public final class RuBurnTimeCache {

    public static final int UNKNOWN = Integer.MIN_VALUE;
    public static final int NOT_HANDLED = -1;

    private static final ConcurrentHashMap<Item, Integer> CACHE = new ConcurrentHashMap<>();

    private RuBurnTimeCache() {
    }

    public static int get(Item item) {
        Integer value = CACHE.get(item);
        return value == null ? UNKNOWN : value;
    }

    public static void record(Item item, int burnTime) {
        CACHE.put(item, burnTime);
    }

    public static void recordUnhandled(Item item) {
        CACHE.putIfAbsent(item, NOT_HANDLED);
    }
}
