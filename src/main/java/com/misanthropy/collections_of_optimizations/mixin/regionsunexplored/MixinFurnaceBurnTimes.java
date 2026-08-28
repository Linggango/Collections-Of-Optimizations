package com.misanthropy.collections_of_optimizations.mixin.regionsunexplored;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.RuBurnTimeCache;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.regions_unexplored.block.compat.FurnaceBurnTimes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FurnaceBurnTimes.class, remap = false)
public abstract class MixinFurnaceBurnTimes {

    @Inject(
            method = "burnTime(Lnet/minecraftforge/event/furnace/FurnaceFuelBurnTimeEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$cachedBurnTime(FurnaceFuelBurnTimeEvent event, CallbackInfo ci) {
        if (!CoOConfig.regionsunexploredCacheFurnaceBurnTimes) {
            return;
        }

        int cached = RuBurnTimeCache.get(event.getItemStack().getItem());
        if (cached == RuBurnTimeCache.UNKNOWN) {
            return;
        }

        if (cached != RuBurnTimeCache.NOT_HANDLED) {
            event.setBurnTime(cached);
        }
        ci.cancel();
    }

    @WrapOperation(
            method = "burnTime(Lnet/minecraftforge/event/furnace/FurnaceFuelBurnTimeEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/event/furnace/FurnaceFuelBurnTimeEvent;setBurnTime(I)V"
            ),
            require = 0
    )
    private static void coo$recordMatch(FurnaceFuelBurnTimeEvent event, int burnTime, Operation<Void> original) {
        if (CoOConfig.regionsunexploredCacheFurnaceBurnTimes) {
            RuBurnTimeCache.record(event.getItemStack().getItem(), burnTime);
        }
        original.call(event, burnTime);
    }

    @Inject(
            method = "burnTime(Lnet/minecraftforge/event/furnace/FurnaceFuelBurnTimeEvent;)V",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$recordMiss(FurnaceFuelBurnTimeEvent event, CallbackInfo ci) {
        if (CoOConfig.regionsunexploredCacheFurnaceBurnTimes) {
            RuBurnTimeCache.recordUnhandled(event.getItemStack().getItem());
        }
    }
}
