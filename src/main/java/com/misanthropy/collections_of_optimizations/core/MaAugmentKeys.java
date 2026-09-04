package com.misanthropy.collections_of_optimizations.core;

import com.blakebr0.mysticalagriculture.api.MysticalAgricultureAPI;
import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.ITinkerable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MaAugmentKeys {

    private static final String[] SLOT_KEYS = new String[16];

    private static final Map<String, Augment> BY_ID = new ConcurrentHashMap<>();

    static {
        for (int i = 0; i < SLOT_KEYS.length; i++) {
            SLOT_KEYS[i] = "Augment-" + i;
        }
    }

    private MaAugmentKeys() {
    }

    public static void collect(ItemStack stack, List<Augment> out) {
        var nbt = stack.getTag();
        if (nbt == null) {
            return;
        }
        if (!(stack.getItem() instanceof ITinkerable tinkerable)) {
            return;
        }
        int slots = tinkerable.getAugmentSlots();
        for (int i = 0; i < slots; i++) {
            String key = i < SLOT_KEYS.length ? SLOT_KEYS[i] : "Augment-" + i;
            if (!nbt.contains(key)) {
                continue;
            }
            Augment augment = byId(nbt.getString(key));
            if (augment != null) {
                out.add(augment);
            }
        }
    }

    private static Augment byId(String id) {
        Augment cached = BY_ID.get(id);
        if (cached != null) {
            return cached;
        }
        Augment augment = MysticalAgricultureAPI.getAugmentRegistry().getAugmentById(new ResourceLocation(id));
        if (augment != null) {
            BY_ID.put(id, augment);
        }
        return augment;
    }
}
