package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModAttributes;

public final class TerraCurioAggroState {

    private static volatile Snapshot snapshot;

    private TerraCurioAggroState() {
    }

    public static boolean anyAggro(Level level) {
        if (level == null) {
            return true;
        }

        ResourceKey<Level> dimension = level.dimension();
        long stamp = level.getGameTime();

        Snapshot current = snapshot;
        if (current != null && current.dimension() == dimension && current.stamp() == stamp) {
            return current.result();
        }

        boolean result = scan(level);
        snapshot = new Snapshot(dimension, stamp, result);
        return result;
    }

    private static boolean scan(Level level) {
        Attribute aggro;
        try {
            aggro = ModAttributes.getAggro();
        } catch (RuntimeException exception) {
            return true;
        }
        if (aggro == null) {
            return true;
        }

        for (Player player : level.players()) {
            AttributeInstance instance = player.getAttribute(aggro);
            if (instance != null && instance.getValue() != 0.0D) {
                return true;
            }
        }
        return false;
    }

    private record Snapshot(ResourceKey<Level> dimension, long stamp, boolean result) {
    }
}
