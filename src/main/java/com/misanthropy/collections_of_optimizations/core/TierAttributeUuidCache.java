package com.misanthropy.collections_of_optimizations.core;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class TierAttributeUuidCache {

    private static final int LIMIT = 4096;

    private static final Map<Key, UUID> CACHE = new ConcurrentHashMap<>();

    private TierAttributeUuidCache() {
    }

    public static UUID lookup(byte[] name) {
        if (name == null) {
            return null;
        }
        return CACHE.get(new Key(name));
    }

    public static void store(byte[] name, UUID value) {
        if (name == null || value == null) {
            return;
        }
        if (CACHE.size() >= LIMIT) {
            CACHE.clear();
        }
        CACHE.put(new Key(name), value);
    }

    private static final class Key {

        private final byte[] bytes;
        private final int hash;

        private Key(byte[] bytes) {
            this.bytes = bytes;
            this.hash = Arrays.hashCode(bytes);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return other instanceof Key key && this.hash == key.hash && Arrays.equals(this.bytes, key.bytes);
        }

        @Override
        public int hashCode() {
            return this.hash;
        }
    }
}
