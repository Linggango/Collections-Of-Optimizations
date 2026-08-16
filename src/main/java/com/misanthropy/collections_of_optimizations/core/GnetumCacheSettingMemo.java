package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;

public final class GnetumCacheSettingMemo {

    public static final byte UNKNOWN = -1;

    private static final int SLOTS = 4;

    private static final Object2ByteOpenHashMap<String>[] MEMO = newSlots();

    private static int stamp = Integer.MIN_VALUE;

    private GnetumCacheSettingMemo() {
    }

    @SuppressWarnings("unchecked")
    private static Object2ByteOpenHashMap<String>[] newSlots() {
        Object2ByteOpenHashMap<String>[] slots = new Object2ByteOpenHashMap[SLOTS];
        for (int i = 0; i < SLOTS; i++) {
            slots[i] = new Object2ByteOpenHashMap<>(32);
            slots[i].defaultReturnValue(UNKNOWN);
        }
        return slots;
    }

    public static byte get(int slot, String id, int tick) {
        if (tick != stamp) {
            stamp = tick;
            for (int i = 0; i < SLOTS; i++) {
                MEMO[i].clear();
            }
            return UNKNOWN;
        }
        return MEMO[slot].getByte(id);
    }

    public static void record(int slot, String id, boolean disabled) {
        MEMO[slot].put(id, disabled ? (byte) 1 : (byte) 0);
    }
}
