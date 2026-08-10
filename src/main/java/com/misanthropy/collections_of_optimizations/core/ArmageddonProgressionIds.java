package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ArmageddonProgressionIds {

    private static final Map<String, ResourceLocation> PARSED = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, String> PRINTED = new ConcurrentHashMap<>();

    private ArmageddonProgressionIds() {
    }

    public static ResourceLocation parse(String id) {
        ResourceLocation cached = PARSED.get(id);
        return cached != null ? cached : PARSED.computeIfAbsent(id, ResourceLocation::parse);
    }

    public static String print(ResourceLocation id) {
        String cached = PRINTED.get(id);
        return cached != null ? cached : PRINTED.computeIfAbsent(id, ResourceLocation::toString);
    }
}
