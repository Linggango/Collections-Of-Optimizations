package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;

import java.util.Arrays;

public final class BiomeBlendCache {

    public static final int MAX_RADIUS = 64;

    private static final long MIX = 0x9E3779B97F4A7C15L;

    private static final int SAMPLE_SLOTS = 4096;
    private static final int SAMPLE_SHIFT = 52;
    private static final int ROW_SLOTS = 1024;
    private static final int ROW_SHIFT = 54;

    private static final int NO_SAMPLE = -1;
    private static final long NO_ROW = -1L;

    private static final ThreadLocal<BiomeBlendCache> LOCAL = ThreadLocal.withInitial(BiomeBlendCache::new);

    private static volatile int generation;

    private final Reference2ObjectOpenHashMap<ColorResolver, Table> tables = new Reference2ObjectOpenHashMap<>(4);

    private ColorResolver lastResolver;
    private Table lastTable;
    private int seenGeneration = generation;

    private BiomeBlendCache() {
    }

    public static void invalidate() {
        generation++;
    }

    public static int blend(ClientLevel level, ColorResolver resolver, BlockPos pos, int radius) {
        return LOCAL.get().tableFor(resolver).blend(level, resolver, pos.getX(), pos.getY(), pos.getZ(), radius);
    }

    private Table tableFor(ColorResolver resolver) {
        int now = generation;
        if (now != this.seenGeneration) {
            this.seenGeneration = now;
            for (Table table : this.tables.values()) {
                table.clear();
            }
        }
        if (resolver == this.lastResolver) {
            return this.lastTable;
        }
        Table table = this.tables.get(resolver);
        if (table == null) {
            table = new Table();
            this.tables.put(resolver, table);
        }
        this.lastResolver = resolver;
        this.lastTable = table;
        return table;
    }

    private static final class Table {

        private final long[] sampleKeys = new long[SAMPLE_SLOTS];
        private final int[] sampleColors = new int[SAMPLE_SLOTS];
        private final long[] rowKeys = new long[ROW_SLOTS];
        private final long[] rowSums = new long[ROW_SLOTS];
        private final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        private int rowRadius = -1;

        private Table() {
            clear();
        }

        private void clear() {
            Arrays.fill(this.sampleColors, NO_SAMPLE);
            Arrays.fill(this.rowSums, NO_ROW);
        }

        private int blend(ClientLevel level, ColorResolver resolver, int x, int y, int z, int radius) {
            if (radius != this.rowRadius) {
                this.rowRadius = radius;
                Arrays.fill(this.rowSums, NO_ROW);
            }

            int red = 0;
            int green = 0;
            int blue = 0;

            for (int dz = -radius; dz <= radius; ++dz) {
                long row = rowSum(level, resolver, x, y, z + dz, radius);
                red += (int) (row >>> 42);
                green += (int) ((row >>> 21) & 0x1FFFFFL);
                blue += (int) (row & 0x1FFFFFL);
            }

            int diameter = radius * 2 + 1;
            int total = diameter * diameter;
            return (red / total & 255) << 16 | (green / total & 255) << 8 | blue / total & 255;
        }

        private long rowSum(ClientLevel level, ColorResolver resolver, int x, int y, int z, int radius) {
            long key = BlockPos.asLong(x, y, z);
            int slot = (int) ((key * MIX) >>> ROW_SHIFT);
            if (this.rowKeys[slot] == key) {
                long cached = this.rowSums[slot];
                if (cached != NO_ROW) {
                    return cached;
                }
            }

            int red = 0;
            int green = 0;
            int blue = 0;

            for (int dx = -radius; dx <= radius; ++dx) {
                int color = sample(level, resolver, x + dx, y, z);
                red += (color >> 16) & 255;
                green += (color >> 8) & 255;
                blue += color & 255;
            }

            long packed = (long) red << 42 | (long) green << 21 | blue;
            this.rowKeys[slot] = key;
            this.rowSums[slot] = packed;
            return packed;
        }

        private int sample(ClientLevel level, ColorResolver resolver, int x, int y, int z) {
            long key = BlockPos.asLong(x, y, z);
            int slot = (int) ((key * MIX) >>> SAMPLE_SHIFT);
            if (this.sampleKeys[slot] == key) {
                int cached = this.sampleColors[slot];
                if (cached != NO_SAMPLE) {
                    return cached;
                }
            }

            this.cursor.set(x, y, z);
            int color = resolver.getColor(level.getBiome(this.cursor).value(), x, z) & 0xFFFFFF;
            this.sampleKeys[slot] = key;
            this.sampleColors[slot] = color;
            return color;
        }
    }
}
