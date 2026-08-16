package com.misanthropy.collections_of_optimizations.core;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public final class CameraShakeScanCache {

    private static final Map<Class<?>, List<Entity>> CACHE = new Reference2ObjectOpenHashMap<>(16);

    private static WeakReference<Level> scanned = new WeakReference<>(null);

    private static long stamp = Long.MIN_VALUE;

    private CameraShakeScanCache() {
    }

    public static List<Entity> get(Level level, Class<Entity> type, AABB box, Operation<List<Entity>> original) {
        if (type == null || !Minecraft.getInstance().isSameThread()) {
            return original.call(level, type, box);
        }
        long now = level.getGameTime();
        if (stamp != now || scanned.get() != level) {
            stamp = now;
            scanned = new WeakReference<>(level);
            CACHE.clear();
        }
        List<Entity> cached = CACHE.get(type);
        if (cached == null) {
            cached = original.call(level, type, box);
            CACHE.put(type, cached);
        }
        return cached;
    }
}
