package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class CncWechugeFogCache {

    private static final double RADIUS = 24.0;

    private static int stamp = Integer.MIN_VALUE;

    private static boolean near;

    private CncWechugeFogCache() {
    }

    public static boolean nearCamera(LevelAccessor level, Vec3 position) {
        int now = ClientTickStamp.currentOnRenderThread();
        if (now != stamp || now < 0) {
            stamp = now;
            near = !level.getEntities(
                    (Entity) null,
                    AABB.ofSize(position, RADIUS, RADIUS, RADIUS),
                    ModEntityFilter.CNC_WECHUGE::matches
            ).isEmpty();
        }
        return near;
    }
}
