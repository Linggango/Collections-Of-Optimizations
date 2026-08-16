package com.misanthropy.collections_of_optimizations.core;

public final class VisualEffectLatch {

    private static boolean armed = true;

    private static boolean sawEntity;

    private static boolean sawEffects;

    private VisualEffectLatch() {
    }

    public static boolean armed() {
        return armed;
    }

    public static void arm() {
        armed = true;
    }

    public static void beginPass() {
        sawEntity = false;
        sawEffects = false;
    }

    public static void observe(boolean empty) {
        sawEntity = true;
        if (!empty) {
            sawEffects = true;
        }
    }

    public static void endPass() {
        if (sawEntity && !sawEffects) {
            armed = false;
        }
    }
}
