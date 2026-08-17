package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class MapTintCache {

    private static final Map<Block, Optional<Block>> CACHE = new ConcurrentHashMap<>();

    private MapTintCache() {
    }

    public static Map<Block, Optional<Block>> cache() {
        return CACHE;
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(MapTintCache::onTagsUpdated);
    }

    private static void onTagsUpdated(TagsUpdatedEvent event) {
        CACHE.clear();
    }
}
