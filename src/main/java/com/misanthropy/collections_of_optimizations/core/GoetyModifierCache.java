package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class GoetyModifierCache {

    private static final Map<String, UUID> UUIDS = new ConcurrentHashMap<>();
    private static final Map<UUID, AttributeModifier> MODIFIERS = new ConcurrentHashMap<>();

    private GoetyModifierCache() {
    }

    public static UUID uuid(String text) {
        return UUIDS.get(text);
    }

    public static void storeUuid(String text, UUID uuid) {
        if (text != null && uuid != null) {
            UUIDS.putIfAbsent(text, uuid);
        }
    }

    public static AttributeModifier modifier(UUID id, String name, double amount,
                                            AttributeModifier.Operation operation) {
        if (id == null) {
            return null;
        }
        AttributeModifier cached = MODIFIERS.get(id);
        if (cached != null
                && cached.getAmount() == amount
                && cached.getOperation() == operation
                && cached.getName().equals(name)) {
            return cached;
        }
        return null;
    }

    public static void storeModifier(AttributeModifier modifier) {
        if (modifier != null) {
            MODIFIERS.putIfAbsent(modifier.getId(), modifier);
        }
    }
}
