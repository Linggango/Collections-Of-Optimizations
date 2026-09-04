package com.misanthropy.collections_of_optimizations.mixin.crittersandcompanions;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.Behaviour;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.Behaviours;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Consumer;

@Mixin(value = Behaviours.class, remap = false)
public abstract class MixinCacBehaviours {

    @Shadow
    @Final
    private Map<Class<? extends Behaviour>, Behaviour> entries;

    @Inject(
            method = "the",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$directBehaviourLookup(Class<?> type, CallbackInfoReturnable<Behaviour> cir) {
        if (!CoOConfig.crittersandcompanionsFastBehaviourLookup) {
            return;
        }
        Behaviour found = this.entries.get(type);
        if (found != null) {
            cir.setReturnValue(found);
        }
    }

    @Inject(
            method = "forEach",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipEmptyBehaviourWalk(Consumer<Behaviour> consumer, CallbackInfo ci) {
        if (CoOConfig.crittersandcompanionsFastBehaviourLookup && this.entries.isEmpty()) {
            ci.cancel();
        }
    }
}
