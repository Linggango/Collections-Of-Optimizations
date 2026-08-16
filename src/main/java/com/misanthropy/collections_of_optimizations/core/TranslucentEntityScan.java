package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class TranslucentEntityScan {

    private static final List<Entity> MATCHES = new ArrayList<>();

    private static WeakReference<ClientLevel> scanned = new WeakReference<>(null);

    private static long stamp = Long.MIN_VALUE;

    private TranslucentEntityScan() {
    }

    public static Iterable<Entity> matches(ClientLevel level, EntityRenderDispatcher dispatcher) {
        long now = level.getGameTime();
        if (stamp != now || scanned.get() != level) {
            stamp = now;
            scanned = new WeakReference<>(level);
            MATCHES.clear();
            for (Entity entity : level.entitiesForRendering()) {
                Class<?> entityClass = entity.getClass();
                Boolean known = TranslucentRendererCache.known(entityClass);
                if (known == null) {
                    EntityRenderer<?> renderer = dispatcher.getRenderer(entity);
                    TranslucentRendererCache.record(entityClass, renderer);
                    known = TranslucentRendererCache.known(entityClass);
                }
                if (known == Boolean.TRUE) {
                    MATCHES.add(entity);
                }
            }
        } else if (!MATCHES.isEmpty()) {
            MATCHES.removeIf(Entity::isRemoved);
        }
        return MATCHES;
    }
}
