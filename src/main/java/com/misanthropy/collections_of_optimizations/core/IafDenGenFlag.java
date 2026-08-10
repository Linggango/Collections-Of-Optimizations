package com.misanthropy.collections_of_optimizations.core;

public final class IafDenGenFlag {

    private static final ThreadLocal<Boolean> ACTIVE = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private IafDenGenFlag() {
    }

    public static void enable() {
        ACTIVE.set(Boolean.TRUE);
    }

    public static void disable() {
        ACTIVE.remove();
    }

    public static boolean isActive() {
        return ACTIVE.get();
    }
}
