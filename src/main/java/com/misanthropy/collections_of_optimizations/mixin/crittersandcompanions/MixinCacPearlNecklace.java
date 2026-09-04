package com.misanthropy.collections_of_optimizations.mixin.crittersandcompanions;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CacNecklaceHolder;
import io.github.bonsaistudi0s.crittersandcompanions.common.item.PearlNecklaceItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = PearlNecklaceItem.class, remap = false)
public abstract class MixinCacPearlNecklace {

    @Inject(
            method = "getWearing",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$readNecklaceMemo(Entity entity, CallbackInfoReturnable<Optional<?>> cir) {
        if (!CoOConfig.crittersandcompanionsMemoNecklaceLookup) {
            return;
        }
        if (entity instanceof Player player && player instanceof CacNecklaceHolder holder
                && holder.coo$cacNecklaceStamp() == player.tickCount) {
            cir.setReturnValue(holder.coo$cacNecklace());
        }
    }

    @Inject(
            method = "getWearing",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$storeNecklaceMemo(Entity entity, CallbackInfoReturnable<Optional<?>> cir) {
        if (!CoOConfig.crittersandcompanionsMemoNecklaceLookup) {
            return;
        }
        if (entity instanceof Player player && player instanceof CacNecklaceHolder holder) {
            holder.coo$storeCacNecklace(cir.getReturnValue(), player.tickCount);
        }
    }
}
