package com.misanthropy.collections_of_optimizations.core;

import org.violetmoon.zeta.util.handler.StructureBlockReplacementHandler.StructureFunction;

import java.util.Set;

public final class ZetaStructureFunctions {

    private static StructureFunction[] cached = null;
    private static int cachedSize = -1;

    private ZetaStructureFunctions() {
    }

    public static StructureFunction[] snapshot(Set<StructureFunction> functions) {
        StructureFunction[] local = cached;
        if (local == null || cachedSize != functions.size()) {
            local = functions.toArray(new StructureFunction[0]);
            cached = local;
            cachedSize = local.length;
        }
        return local;
    }
}
