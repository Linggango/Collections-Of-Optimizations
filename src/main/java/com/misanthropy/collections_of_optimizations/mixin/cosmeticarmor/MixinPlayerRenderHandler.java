package com.misanthropy.collections_of_optimizations.mixin.cosmeticarmor;

import com.google.common.cache.LoadingCache;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CosArmorRestoreQueueHolder;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "lain.mods.cos.impl.client.PlayerRenderHandler", remap = false)
public abstract class MixinPlayerRenderHandler {

    @Redirect(
            method = {
                    "handlePreRenderPlayer_High",
                    "handlePostRenderPlayer_Low",
                    "handlePreRenderPlayer_LowestCanceled",
                    "handleRenderHand_High",
                    "handleRenderHand_LowestCanceled",
                    "handleRenderArm_High",
                    "handleRenderArm_LowestCanceled"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/cache/LoadingCache;getUnchecked(Ljava/lang/Object;)Ljava/lang/Object;"
            ),
            require = 0
    )
    private Object coo$perPlayerRestoreQueue(LoadingCache<Object, Object> cache, Object key) {
        if (CoOConfig.cosmeticarmorPerPlayerRestoreQueue
                && key instanceof Player
                && key instanceof CosArmorRestoreQueueHolder holder) {
            return holder.coo$cosArmorRestoreQueue();
        }
        return cache.getUnchecked(key);
    }
}
