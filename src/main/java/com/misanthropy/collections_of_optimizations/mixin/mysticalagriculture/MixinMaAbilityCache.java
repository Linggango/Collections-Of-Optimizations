package com.misanthropy.collections_of_optimizations.mixin.mysticalagriculture;

import com.blakebr0.mysticalagriculture.api.lib.AbilityCache;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(value = AbilityCache.class, remap = false)
public abstract class MixinMaAbilityCache {

    @Shadow
    @Final
    private Map<String, Map<String, Runnable>> cache;

    @Inject(
            method = "getCachedAbilities(Lnet/minecraft/world/entity/player/Player;)Ljava/util/Set;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$leanCachedAbilities(Player player, CallbackInfoReturnable<Set<String>> cir) {
        if (!CoOConfig.mysticalagricultureLeanAbilityCache) {
            return;
        }

        Set<String> cached = new HashSet<>();
        String key = null;

        for (Map.Entry<String, Map<String, Runnable>> entry : this.cache.entrySet()) {
            var users = entry.getValue();
            if (users.isEmpty()) {
                continue;
            }

            if (key == null) {
                key = player.getGameProfile().getName() + ":" + player.level().isClientSide();
            }

            if (users.containsKey(key)) {
                cached.add(entry.getKey());
            }
        }

        cir.setReturnValue(cached);
    }
}
