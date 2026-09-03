package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.level.Level;

import java.util.List;

public interface SummonityMinionCacheHolder {

    List<?> coo$summonityMinions();

    Level coo$summonityMinionsLevel();

    long coo$summonityMinionsStamp();

    long coo$summonityMinionsGeneration();

    void coo$storeSummonityMinions(List<?> minions, Level level, long stamp, long generation);

    int coo$summonitySlotsSent();

    void coo$storeSummonitySlotsSent(int slots);
}
