package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class BossEntityScan {

    private static final List<Entity> MATCHES = new ArrayList<>();

    private static WeakReference<Level> scanned = new WeakReference<>(null);

    private static int stamp = Integer.MIN_VALUE;

    private BossEntityScan() {
    }

    public static List<Entity> matches(Level level) {
        int tick = ClientTickStamp.current();
        if (tick != stamp || scanned.get() != level) {
            stamp = tick;
            scanned = new WeakReference<>(level);
            MATCHES.clear();
            if (level instanceof ClientLevel clientLevel) {
                for (Entity entity : clientLevel.entitiesForRendering()) {
                    if (ModEntityFilter.BOSSES_RISE.matches(entity)) {
                        MATCHES.add(entity);
                    }
                }
            }
        }
        return MATCHES;
    }
}
