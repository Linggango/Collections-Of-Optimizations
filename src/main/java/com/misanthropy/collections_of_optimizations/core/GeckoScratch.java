package com.misanthropy.collections_of_optimizations.core;

import org.joml.Vector3f;

public final class GeckoScratch {

    private static final Vector3f POSITION = new Vector3f();

    private GeckoScratch() {
    }

    public static Vector3f position(boolean reusable) {
        return reusable ? POSITION : new Vector3f();
    }
}
