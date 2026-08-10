package com.misanthropy.collections_of_optimizations.core;

import net.irisshaders.iris.api.v0.IrisApi;

public final class ShadowPass {

    private static Boolean available;

    private ShadowPass() {
    }

    public static boolean active() {
        if (available == null) {
            try {
                IrisApi.getInstance();
                available = Boolean.TRUE;
            } catch (Throwable throwable) {
                available = Boolean.FALSE;
            }
        }
        if (available == Boolean.FALSE) {
            return false;
        }
        try {
            return IrisApi.getInstance().isRenderingShadowPass();
        } catch (Throwable throwable) {
            available = Boolean.FALSE;
            return false;
        }
    }
}
