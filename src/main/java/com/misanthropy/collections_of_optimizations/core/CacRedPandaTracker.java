package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.level.entity.EntityAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;

public final class CacRedPandaTracker {

    private static final String RED_PANDA_CLASS =
            "io.github.bonsaistudi0s.crittersandcompanions.common.entity.RedPandaEntity";

    private static Class<?> redPanda;
    private static boolean resolved;
    private static boolean armed;
    private static int loaded;

    private CacRedPandaTracker() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(CacRedPandaTracker::onServerStopped);
    }

    private static void onServerStopped(ServerStoppedEvent event) {
        loaded = 0;
        armed = false;
    }

    public static void onTrackingStart(EntityAccess entity) {
        armed = true;
        Class<?> type = redPandaClass();
        if (type != null && type.isInstance(entity)) {
            loaded++;
        }
    }

    public static void onTrackingEnd(EntityAccess entity) {
        Class<?> type = redPandaClass();
        if (type != null && type.isInstance(entity)) {
            loaded--;
        }
    }

    public static boolean noneLoaded(Class<?> avoidClass) {
        return avoidClass == redPandaClass() && armed && loaded == 0;
    }

    private static Class<?> redPandaClass() {
        if (!resolved) {
            resolved = true;
            try {
                redPanda = Class.forName(RED_PANDA_CLASS, false, CacRedPandaTracker.class.getClassLoader());
            } catch (Throwable ignored) {
            }
        }
        return redPanda;
    }
}
