package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppingEvent;

import java.lang.reflect.Field;
import java.util.Map;

public final class AlexsMobsLevelMaps {

    private static final String WORLD_DATA_CLASS = "com.github.alexthe666.alexsmobs.world.AMWorldData";
    private static final String SERVER_EVENTS_CLASS = "com.github.alexthe666.alexsmobs.event.ServerEvents";

    private static boolean armed;

    private AlexsMobsLevelMaps() {
    }

    public static void arm() {
        if (armed) {
            return;
        }
        synchronized (AlexsMobsLevelMaps.class) {
            if (armed) {
                return;
            }
            armed = true;
            MinecraftForge.EVENT_BUS.addListener(AlexsMobsLevelMaps::onServerStopping);
        }
    }

    private static void onServerStopping(ServerStoppingEvent event) {
        if (!CoOConfig.alexsmobsReleaseLevelMaps) {
            return;
        }
        clear(WORLD_DATA_CLASS, "dataMap");
        clear(SERVER_EVENTS_CLASS, "BEACHED_CACHALOT_WHALE_SPAWNER_MAP");
    }

    private static void clear(String className, String fieldName) {
        try {
            Field field = Class.forName(className).getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(null);
            if (value instanceof Map<?, ?> map) {
                map.clear();
            }
        } catch (Throwable ignored) {

        }
    }
}
