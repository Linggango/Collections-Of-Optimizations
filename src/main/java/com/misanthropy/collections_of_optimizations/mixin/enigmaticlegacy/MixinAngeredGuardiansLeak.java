package com.misanthropy.collections_of_optimizations.mixin.enigmaticlegacy;

import com.google.common.collect.Multimap;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(targets = "com.aizistral.enigmaticlegacy.handlers.EnigmaticEventHandler", remap = false)
public abstract class MixinAngeredGuardiansLeak {

    @Redirect(
            method = "onAttackTargetSet",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/Multimap;put(Ljava/lang/Object;Ljava/lang/Object;)Z",
                    remap = false
            ),
            require = 0
    )
    private boolean coo$dropRemovedGuardians(Multimap<Object, Object> map, Object key, Object value) {
        if (CoOConfig.enigmaticlegacyPruneAngeredGuardians) {
            map.entries().removeIf(MixinAngeredGuardiansLeak::coo$isStale);
        }
        return map.put(key, value);
    }

    private static boolean coo$isStale(Map.Entry<Object, Object> entry) {
        return entry.getKey() instanceof Entity owner && owner.isRemoved()
                || entry.getValue() instanceof Entity guardian && guardian.isRemoved();
    }
}
