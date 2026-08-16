package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

public final class BigOutlineProbeCache {

    private static final LongOpenHashSet PROBED = new LongOpenHashSet();

    private BigOutlineProbeCache() {
    }

    public static void beginPick() {
        PROBED.clear();
    }

    public static boolean firstProbe(long packedPos) {
        return PROBED.add(packedPos);
    }
}
