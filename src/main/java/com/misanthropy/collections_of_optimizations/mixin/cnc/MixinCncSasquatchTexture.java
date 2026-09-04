package com.misanthropy.collections_of_optimizations.mixin.cnc;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.imasillylittleguy.cnc.procedures.SasquatchOnEntityTickUpdateProcedure", remap = false)
public abstract class MixinCncSasquatchTexture {

    @Inject(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$throttle(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        int interval = CoOConfig.cncSasquatchTextureScanInterval;
        if (interval <= 1 || entity == null) {
            return;
        }
        if (Math.floorMod(entity.tickCount, interval) != 0) {
            ci.cancel();
        }
    }
}
