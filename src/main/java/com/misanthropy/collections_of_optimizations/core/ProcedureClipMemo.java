package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class ProcedureClipMemo {

    private static final ThreadLocal<ProcedureClipMemo> LOCAL = ThreadLocal.withInitial(ProcedureClipMemo::new);

    private Level level;
    private long gameTime;
    private Vec3 from;
    private Vec3 to;
    private Object block;
    private Object fluid;
    private Object owner;
    private BlockHitResult result;

    private ProcedureClipMemo() {
    }

    public static ProcedureClipMemo local() {
        return LOCAL.get();
    }

    public BlockHitResult hit(Level level, Vec3 from, Vec3 to, Object block, Object fluid, Object owner) {
        return this.result != null
                && this.level == level
                && this.gameTime == level.getGameTime()
                && this.block == block
                && this.fluid == fluid
                && this.owner == owner
                && this.from.equals(from)
                && this.to.equals(to)
                ? this.result
                : null;
    }

    public void store(Level level, Vec3 from, Vec3 to, Object block, Object fluid, Object owner, BlockHitResult result) {
        this.level = level;
        this.gameTime = level.getGameTime();
        this.from = from;
        this.to = to;
        this.block = block;
        this.fluid = fluid;
        this.owner = owner;
        this.result = result;
    }
}
