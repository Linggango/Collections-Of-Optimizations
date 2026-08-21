package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

import java.util.function.Predicate;

public final class BwgTerrainChunkFilter {

    private static final ResourceKey<Biome> CRAG_GARDENS =
            ResourceKey.create(Registries.BIOME, new ResourceLocation("biomeswevegone", "crag_gardens"));

    private static final ResourceKey<Biome> BASALT_BARRERA =
            ResourceKey.create(Registries.BIOME, new ResourceLocation("biomeswevegone", "basalt_barrera"));

    private static final Predicate<Holder<Biome>> IS_CRAG_GARDENS = holder -> holder.is(CRAG_GARDENS);

    private static final Predicate<Holder<Biome>> IS_BASALT_BARRERA = holder -> holder.is(BASALT_BARRERA);

    private BwgTerrainChunkFilter() {
    }

    public static boolean hasCragGardens(ChunkAccess chunk) {
        return contains(chunk, IS_CRAG_GARDENS);
    }

    public static boolean hasBasaltBarrera(ChunkAccess chunk) {
        return contains(chunk, IS_BASALT_BARRERA);
    }

    private static boolean contains(ChunkAccess chunk, Predicate<Holder<Biome>> predicate) {
        LevelChunkSection[] sections;
        try {
            sections = chunk.getSections();
        } catch (Throwable ignored) {
            return true;
        }
        if (sections == null || sections.length == 0) {
            return true;
        }
        for (LevelChunkSection section : sections) {
            if (section != null && section.getBiomes().maybeHas(predicate)) {
                return true;
            }
        }
        return false;
    }
}
