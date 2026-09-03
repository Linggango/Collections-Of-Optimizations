package com.misanthropy.collections_of_optimizations.mixin.summonity;

import com.misanthropy.collections_of_optimizations.core.SummonityMinionCacheHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(Player.class)
public abstract class MixinPlayerSummonityCache implements SummonityMinionCacheHolder {

    @Unique
    private List<?> coo$summonityMinions;

    @Unique
    private Level coo$summonityMinionsLevel;

    @Unique
    private long coo$summonityMinionsStamp = Long.MIN_VALUE;

    @Unique
    private long coo$summonityMinionsGeneration = Long.MIN_VALUE;

    @Unique
    private int coo$summonitySlotsSent = Integer.MIN_VALUE;

    @Override
    public List<?> coo$summonityMinions() {
        return this.coo$summonityMinions;
    }

    @Override
    public Level coo$summonityMinionsLevel() {
        return this.coo$summonityMinionsLevel;
    }

    @Override
    public long coo$summonityMinionsStamp() {
        return this.coo$summonityMinionsStamp;
    }

    @Override
    public long coo$summonityMinionsGeneration() {
        return this.coo$summonityMinionsGeneration;
    }

    @Override
    public void coo$storeSummonityMinions(List<?> minions, Level level, long stamp, long generation) {
        this.coo$summonityMinions = minions;
        this.coo$summonityMinionsLevel = level;
        this.coo$summonityMinionsStamp = stamp;
        this.coo$summonityMinionsGeneration = generation;
    }

    @Override
    public int coo$summonitySlotsSent() {
        return this.coo$summonitySlotsSent;
    }

    @Override
    public void coo$storeSummonitySlotsSent(int slots) {
        this.coo$summonitySlotsSent = slots;
    }
}
