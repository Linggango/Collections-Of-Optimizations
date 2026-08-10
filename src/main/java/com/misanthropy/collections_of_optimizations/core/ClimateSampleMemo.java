package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.level.biome.Climate;

public final class ClimateSampleMemo {

    private static final ThreadLocal<ClimateSampleMemo> LOCAL = ThreadLocal.withInitial(ClimateSampleMemo::new);

    private Object sampler;
    private int x;
    private int y;
    private int z;
    private Climate.TargetPoint result;

    private ClimateSampleMemo() {
    }

    public static ClimateSampleMemo get() {
        return LOCAL.get();
    }

    public Climate.TargetPoint lookup(Object sampler, int x, int y, int z) {
        if (this.result != null && this.sampler == sampler && this.x == x && this.y == y && this.z == z) {
            return this.result;
        }
        return null;
    }

    public void store(Object sampler, int x, int y, int z, Climate.TargetPoint result) {
        this.sampler = sampler;
        this.x = x;
        this.y = y;
        this.z = z;
        this.result = result;
    }
}
