package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class WeaponAttributesCache {

    private static final Object MISS = new Object();

    private static final Map<Item, Object> CACHE = new ConcurrentHashMap<>();

    private WeaponAttributesCache() {
    }

    public static Object lookup(Item item) {
        return item == null ? null : CACHE.get(item);
    }

    public static Object unwrap(Object cached) {
        return cached == MISS ? null : cached;
    }

    public static void store(Item item, Object attributes) {
        if (item != null) {
            CACHE.put(item, attributes == null ? MISS : attributes);
        }
    }

    public static void clear() {
        CACHE.clear();
    }
}
