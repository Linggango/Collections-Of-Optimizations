package com.misanthropy.collections_of_optimizations.mixin.zeta;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.violetmoon.zeta.util.handler.StructureBlockReplacementHandler;
import org.violetmoon.zeta.util.handler.StructureBlockReplacementHandler.StructureFunction;
import org.violetmoon.zeta.util.handler.StructureBlockReplacementHandler.StructureHolder;

import java.util.Set;

@Mixin(value = StructureBlockReplacementHandler.class, remap = false)
public interface ZetaStructureReplacementAccessor {

    @Accessor("functions")
    static Set<StructureFunction> coo$functions() {
        throw new AssertionError();
    }

    @Invoker("getCurrentStructureHolder")
    static StructureHolder coo$currentStructureHolder() {
        throw new AssertionError();
    }
}
