package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

public final class ZetaWrapperMemo {

    private static final Map<Function<?, ?>, Function<?, ?>> MEMOS = new IdentityHashMap<>();

    private ZetaWrapperMemo() {
    }

    @SuppressWarnings("unchecked")
    public static synchronized <A, B> Function<A, B> memoise(Function<A, B> original) {
        if (original == null) {
            return null;
        }
        if (original instanceof Memo) {
            return original;
        }
        return (Function<A, B>) MEMOS.computeIfAbsent(original, f -> new Memo<>((Function<Object, Object>) f));
    }

    private static final class Memo<A, B> implements Function<A, B> {

        private final Function<Object, Object> delegate;
        private final ThreadLocal<Object[]> slot = ThreadLocal.withInitial(() -> new Object[2]);

        private Memo(Function<Object, Object> delegate) {
            this.delegate = delegate;
        }

        @Override
        @SuppressWarnings("unchecked")
        public B apply(A event) {
            if (!CoOConfig.zetaShareEventWrappers) {
                return (B) this.delegate.apply(event);
            }
            Object[] cache = this.slot.get();
            if (cache[0] == event) {
                return (B) cache[1];
            }
            Object wrapper = this.delegate.apply(event);
            cache[0] = event;
            cache[1] = wrapper;
            return (B) wrapper;
        }
    }
}
