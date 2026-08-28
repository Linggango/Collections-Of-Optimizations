package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class GoetyDelightLogItems {

    private static final AtomicInteger GENERATION = new AtomicInteger(1);

    private static volatile List<Item> cached;
    private static volatile int cachedGeneration;

    private GoetyDelightLogItems() {
    }

    public static int generation() {
        return GENERATION.get();
    }

    public static List<Item> peek() {
        List<Item> snapshot = cached;
        return snapshot != null && cachedGeneration == GENERATION.get() ? snapshot : null;
    }

    public static void store(int generation, List<Item> items) {
        if (items == null || generation != GENERATION.get()) {
            return;
        }
        cached = List.copyOf(items);
        cachedGeneration = generation;
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(GoetyDelightLogItems::onTagsUpdated);
    }

    private static void onTagsUpdated(TagsUpdatedEvent event) {
        GENERATION.incrementAndGet();
        cached = null;
    }
}
