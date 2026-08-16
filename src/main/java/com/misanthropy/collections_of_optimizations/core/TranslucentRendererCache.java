package com.misanthropy.collections_of_optimizations.core;

import java.util.IdentityHashMap;
import java.util.Map;

public final class TranslucentRendererCache {

    private static final String MARKER = "cofh.lib.client.renderer.entity.ITranslucentRenderer";

    private static final Map<Class<?>, Boolean> BY_ENTITY_CLASS = new IdentityHashMap<>();

    private TranslucentRendererCache() {
    }

    public static Boolean known(Class<?> entityClass) {
        return BY_ENTITY_CLASS.get(entityClass);
    }

    public static void record(Class<?> entityClass, Object renderer) {
        BY_ENTITY_CLASS.put(entityClass, renderer != null && implementsMarker(renderer.getClass()));
    }

    private static boolean implementsMarker(Class<?> type) {
        for (Class<?> current = type; current != null; current = current.getSuperclass()) {
            for (Class<?> iface : current.getInterfaces()) {
                if (MARKER.equals(iface.getName()) || implementsMarker(iface)) {
                    return true;
                }
            }
        }
        return false;
    }
}
