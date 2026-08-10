package com.misanthropy.collections_of_optimizations.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.settings.ModSettings;

import java.io.IOException;

public final class WaypointSaveQueue {

    private static ModSettings pendingSettings;
    private static WaypointWorld pendingWorld;
    private static boolean registered;

    private WaypointSaveQueue() {
    }

    public static void queue(ModSettings settings, WaypointWorld world) {
        pendingSettings = settings;
        pendingWorld = world;
        register();
    }

    private static synchronized void register() {
        if (!registered) {
            registered = true;
            MinecraftForge.EVENT_BUS.addListener(WaypointSaveQueue::onClientTick);
        }
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || pendingWorld == null) {
            return;
        }

        ModSettings settings = pendingSettings;
        WaypointWorld world = pendingWorld;
        pendingSettings = null;
        pendingWorld = null;

        try {
            settings.saveWaypoints(world);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
