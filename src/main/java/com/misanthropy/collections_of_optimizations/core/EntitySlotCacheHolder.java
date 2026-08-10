package com.misanthropy.collections_of_optimizations.core;

public interface EntitySlotCacheHolder {

    int coo$slotCacheGeneration();

    boolean coo$slotCacheValue();

    void coo$storeSlotCache(int generation, boolean value);
}
