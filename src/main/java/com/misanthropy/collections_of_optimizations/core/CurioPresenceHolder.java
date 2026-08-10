package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.item.Item;

import java.util.Set;

public interface CurioPresenceHolder {

    Set<Item> coo$curioPresence();

    long coo$curioPresenceStamp();

    void coo$storeCurioPresence(Set<Item> items, long stamp);

    void coo$invalidateCurioPresence();
}
