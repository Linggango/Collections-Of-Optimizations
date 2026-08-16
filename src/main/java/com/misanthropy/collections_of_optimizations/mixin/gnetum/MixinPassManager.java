package com.misanthropy.collections_of_optimizations.mixin.gnetum;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ClientTickStamp;
import com.misanthropy.collections_of_optimizations.core.GnetumCacheSettingMemo;
import me.decce.gnetum.ElementType;
import me.decce.gnetum.PassManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PassManager.class, remap = false)
public abstract class MixinPassManager {

    @WrapMethod(method = "cachingDisabled(Ljava/lang/String;)Z", require = 0)
    private boolean coo$memoCacheSetting(String id, Operation<Boolean> original) {
        if (!CoOConfig.gnetumMemoCacheSettings || id == null) {
            return original.call(id);
        }

        int tick = ClientTickStamp.currentOnRenderThread();
        if (tick == -1) {
            return original.call(id);
        }

        byte cached = GnetumCacheSettingMemo.get(0, id, tick);
        if (cached != GnetumCacheSettingMemo.UNKNOWN) {
            return cached != 0;
        }

        boolean disabled = original.call(id);
        GnetumCacheSettingMemo.record(0, id, disabled);
        return disabled;
    }

    @WrapMethod(method = "cachingDisabled(Ljava/lang/String;Lme/decce/gnetum/ElementType;)Z", require = 0)
    private boolean coo$memoTypedCacheSetting(String id, ElementType type, Operation<Boolean> original) {
        if (!CoOConfig.gnetumMemoCacheSettings || id == null || type == null) {
            return original.call(id, type);
        }

        int tick = ClientTickStamp.currentOnRenderThread();
        if (tick == -1) {
            return original.call(id, type);
        }

        int slot = type.ordinal() + 1;
        byte cached = GnetumCacheSettingMemo.get(slot, id, tick);
        if (cached != GnetumCacheSettingMemo.UNKNOWN) {
            return cached != 0;
        }

        boolean disabled = original.call(id, type);
        GnetumCacheSettingMemo.record(slot, id, disabled);
        return disabled;
    }
}
