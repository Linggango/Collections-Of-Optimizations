package com.misanthropy.collections_of_optimizations.core;

import it.hurts.octostudios.perception.common.modules.trail.config.data.TrailConfigData;

public final class PerceptionTrailDefaults {

    private static final TrailConfigData SHARED = new TrailConfigData();

    private PerceptionTrailDefaults() {
    }

    public static TrailConfigData shared() {
        return SHARED;
    }
}
