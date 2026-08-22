package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.Map;

public final class TerraCurioAttributeMirror {

    private static volatile Map<Attribute, Attribute> mirror;

    private TerraCurioAttributeMirror() {
    }

    public static void snapshot(Map<Attribute, Attribute> source) {
        mirror = source == null || source.isEmpty()
                ? Map.of()
                : new Reference2ReferenceOpenHashMap<>(source);
    }

    public static Map<Attribute, Attribute> mirror() {
        return mirror;
    }
}
