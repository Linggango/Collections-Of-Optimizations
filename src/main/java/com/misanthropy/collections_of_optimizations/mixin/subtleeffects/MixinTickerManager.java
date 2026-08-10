package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import einstein.subtle_effects.ticking.tickers.Ticker;
import einstein.subtle_effects.ticking.tickers.TickerManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Consumer;

@Mixin(value = TickerManager.class, remap = false)
public abstract class MixinTickerManager {

    @Shadow
    @Final
    private static List<Ticker> TICKERS;

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V",
                    ordinal = 1
            ),
            require = 0,
            expect = 0
    )
    private static void coo$sweepRemovedTickers(List<Ticker> removeQueue, Consumer<Ticker> action, Operation<Void> original) {
        if (!CoOConfig.subtleeffectsLeanTickerRemoval) {
            original.call(removeQueue, action);
            return;
        }

        if (!removeQueue.isEmpty()) {
            TICKERS.removeIf(Ticker::isRemoved);
        }
    }
}
