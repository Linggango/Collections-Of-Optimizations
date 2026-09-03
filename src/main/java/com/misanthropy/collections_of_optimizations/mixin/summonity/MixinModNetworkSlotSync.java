package com.misanthropy.collections_of_optimizations.mixin.summonity;

import com.fevzi.summonity.entity.MinionHelper;
import com.fevzi.summonity.network.ModNetwork;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.SummonityMinionCacheHolder;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ModNetwork.class, remap = false)
public abstract class MixinModNetworkSlotSync {

    @WrapMethod(method = "syncSummonSlots(Lnet/minecraft/server/level/ServerPlayer;)V")
    private static void coo$skipUnchangedSlotSync(ServerPlayer player, Operation<Void> original) {
        if (CoOConfig.summonitySkipUnchangedSlotSync && player instanceof SummonityMinionCacheHolder holder) {
            int slots = MinionHelper.getUsedMinionSlots(player.serverLevel(), player);
            if (holder.coo$summonitySlotsSent() == slots) {
                return;
            }
            holder.coo$storeSummonitySlotsSent(slots);
        }
        original.call(player);
    }
}
