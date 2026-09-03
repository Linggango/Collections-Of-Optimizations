package com.misanthropy.collections_of_optimizations.core;

import com.fevzi.summonity.entity.SummonedMinion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;

import java.util.List;

public final class SummonityMinionCache {

    private static long generation;

    private SummonityMinionCache() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(SummonityMinionCache::onEntityJoin);
        MinecraftForge.EVENT_BUS.addListener(SummonityMinionCache::onEntityLeave);
    }

    private static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof SummonedMinion) {
            generation++;
        }
    }

    private static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof SummonedMinion) {
            generation++;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<SummonedMinion> cached(Level level, Player owner) {
        if (!(owner instanceof SummonityMinionCacheHolder holder)) {
            return null;
        }
        List<SummonedMinion> minions = (List<SummonedMinion>) holder.coo$summonityMinions();
        if (minions == null
                || holder.coo$summonityMinionsLevel() != level
                || holder.coo$summonityMinionsStamp() != level.getGameTime()
                || holder.coo$summonityMinionsGeneration() != generation) {
            return null;
        }
        return minions;
    }

    public static void store(Level level, Player owner, List<SummonedMinion> minions) {
        if (owner instanceof SummonityMinionCacheHolder holder && minions != null) {
            holder.coo$storeSummonityMinions(minions, level, level.getGameTime(), generation);
        }
    }
}
