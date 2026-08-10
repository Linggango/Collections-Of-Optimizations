package com.misanthropy.collections_of_optimizations.core;

import dev.shadowsoffire.placebo.events.GetEnchantmentLevelEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.ListenerList;
import net.minecraftforge.eventbus.api.EventPriority;

import java.util.Collections;
import java.util.function.Consumer;

public final class PlaceboEnchantEventProbe {

    private static final ListenerList LIST = resolveList();
    private static final int BUS_ID = LIST == null ? -1 : resolveBusId(LIST);

    private PlaceboEnchantEventProbe() {
    }

    public static boolean hasListeners() {
        return BUS_ID < 0 || LIST.getListeners(BUS_ID).length != 0;
    }

    private static ListenerList resolveList() {
        try {
            return new GetEnchantmentLevelEvent(ItemStack.EMPTY, Collections.emptyMap()).getListenerList();
        } catch (Throwable throwable) {
            return null;
        }
    }

    private static int resolveBusId(ListenerList list) {
        Consumer<GetEnchantmentLevelEvent> probe = event -> {
        };
        int found = -1;
        try {
            MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, GetEnchantmentLevelEvent.class, probe);
            try {
                for (int id = 0; ; id++) {
                    if (list.getListeners(id).length != 0) {
                        if (found != -1) {
                            return -1;
                        }
                        found = id;
                    }
                }
            } catch (IndexOutOfBoundsException expected) {
                return found;
            }
        } catch (Throwable throwable) {
            return -1;
        } finally {
            try {
                MinecraftForge.EVENT_BUS.unregister(probe);
            } catch (Throwable ignored) {
            }
        }
    }
}
