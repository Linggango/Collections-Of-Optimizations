package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.entity.Entity;

public final class CncTickState {

    private static volatile long lastCaribouTick = -1000L;

    private CncTickState() {
    }

    public static void markCaribouTick(Entity caribou) {
        lastCaribouTick = caribou.level().getGameTime();
    }

    public static boolean caribouTickedRecently(Entity entity) {
        return entity.level().getGameTime() - lastCaribouTick <= 1L;
    }
}
