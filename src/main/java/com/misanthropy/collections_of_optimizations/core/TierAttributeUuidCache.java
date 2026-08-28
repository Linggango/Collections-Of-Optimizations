package com.misanthropy.collections_of_optimizations.core;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class TierAttributeUuidCache {

    private static final int LIMIT = 4096;

    private static final byte[] EMPTY = new byte[0];

    private static final Map<Key, UUID> CACHE = new ConcurrentHashMap<>();

    private static final ThreadLocal<Key> PROBE = ThreadLocal.withInitial(() -> new Key(EMPTY));

    private TierAttributeUuidCache() {
    }

    public static UUID lookup(byte[] name) {
        if (name == null) {
            return null;
        }
        return CACHE.get(PROBE.get().set(name));
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

        private byte[] bytes;
        private int hash;

        private Key(byte[] bytes) {
            set(bytes);
        }

        private Key set(byte[] value) {
            this.bytes = value;
            this.hash = Arrays.hashCode(value);
            return this;
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
