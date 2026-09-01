package com.misanthropy.collections_of_optimizations.mixin.enigmaticlegacy;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.aizistral.enigmaticlegacy.items.GuardianHeart", remap = false)
public abstract class MixinGuardianHeartScan {

    @Inject(method = {"m_6883_", "inventoryTick"}, at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$throttleMonsterScan(ItemStack stack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        int interval = CoOConfig.enigmaticlegacyGuardianHeartScanInterval;
        if (interval <= 1 || entity == null || level.isClientSide) {
            return;
        }
        if (entity.tickCount % interval != 0) {
            ci.cancel();
        }
    }
}
