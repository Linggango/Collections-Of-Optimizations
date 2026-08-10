package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;

public final class ArsEngGenericInvFilter {

    private static final ClassValue<Boolean> OVERRIDES_GET_CAPABILITY = new ClassValue<>() {
        @Override
        protected Boolean computeValue(Class<?> type) {
            try {
                Class<?> declaring = type
                        .getMethod("getCapability", Capability.class, Direction.class)
                        .getDeclaringClass();
                return declaring != CapabilityProvider.class;
            } catch (Throwable t) {
                return Boolean.TRUE;
            }
        }
    };

    private ArsEngGenericInvFilter() {
    }

    public static boolean mayProvideGenericInv(Class<?> blockEntityClass) {
        return OVERRIDES_GET_CAPABILITY.get(blockEntityClass);
    }
}
