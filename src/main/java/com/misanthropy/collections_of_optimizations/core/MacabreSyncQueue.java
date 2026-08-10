package com.misanthropy.collections_of_optimizations.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MacabreSyncQueue {

    private static final Map<Object, Pending> PENDING = new LinkedHashMap<>();

    private static boolean registered;

    private MacabreSyncQueue() {
    }

    public static boolean queue(Object holder, SimpleChannel channel, PacketDistributor.PacketTarget target, Object message) {
        if (holder == null || channel == null || target == null || message == null) {
            return false;
        }
        register();
        synchronized (PENDING) {
            PENDING.put(holder, new Pending(channel, target, message));
        }
        return true;
    }

    private static synchronized void register() {
        if (!registered) {
            registered = true;

            MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, MacabreSyncQueue::onServerTick);
            MinecraftForge.EVENT_BUS.addListener(MacabreSyncQueue::onServerStopping);
        }
    }

    private static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        List<Pending> due;
        synchronized (PENDING) {
            if (PENDING.isEmpty()) {
                return;
            }
            due = new ArrayList<>(PENDING.values());
            PENDING.clear();
        }
        for (Pending pending : due) {
            pending.channel.send(pending.target, pending.message);
        }
    }

    private static void onServerStopping(ServerStoppingEvent event) {
        synchronized (PENDING) {
            PENDING.clear();
        }
    }

    private record Pending(SimpleChannel channel, PacketDistributor.PacketTarget target, Object message) {
    }
}
