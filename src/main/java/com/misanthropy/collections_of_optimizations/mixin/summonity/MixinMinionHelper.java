package com.misanthropy.collections_of_optimizations.mixin.summonity;

import com.fevzi.summonity.entity.MinionHelper;
import com.fevzi.summonity.entity.SummonedMinion;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.SummonityMinionCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = MinionHelper.class, remap = false)
public abstract class MixinMinionHelper {

    @Inject(method = "getOwnedMinions", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$answerFromTickCache(ServerLevel level, Player owner, CallbackInfoReturnable<List<SummonedMinion>> cir) {
        if (!CoOConfig.summonityCacheOwnedMinionScans) {
            return;
        }
        List<SummonedMinion> cached = SummonityMinionCache.cached(level, owner);
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }

    @Inject(method = "getOwnedMinions", at = @At("RETURN"), require = 0)
    private static void coo$rememberScan(ServerLevel level, Player owner, CallbackInfoReturnable<List<SummonedMinion>> cir) {
        if (CoOConfig.summonityCacheOwnedMinionScans) {
            SummonityMinionCache.store(level, owner, cir.getReturnValue());
        }
    }
}
