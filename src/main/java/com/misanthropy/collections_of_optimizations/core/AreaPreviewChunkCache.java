package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;

public final class AreaPreviewChunkCache {

    public static final byte UNKNOWN = 0;
    public static final byte PRESENT = 1;
    public static final byte ABSENT = 2;

    private static final Long2ByteOpenHashMap CHUNKS = new Long2ByteOpenHashMap();

    private static int stamp = Integer.MIN_VALUE;

    private AreaPreviewChunkCache() {
    }

    public static byte state(int tick, long chunkKey) {
        if (tick != stamp) {
            stamp = tick;
            CHUNKS.clear();
            return UNKNOWN;
        }
        return CHUNKS.get(chunkKey);
    }

    public static void record(long chunkKey, boolean present) {
        CHUNKS.put(chunkKey, present ? PRESENT : ABSENT);
    }
}
