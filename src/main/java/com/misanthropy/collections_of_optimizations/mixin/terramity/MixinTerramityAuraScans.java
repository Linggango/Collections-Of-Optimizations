package com.misanthropy.collections_of_optimizations.mixin.terramity;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mcreator.terramity.procedures.MalwareWhileBaubleIsEquippedTickProcedure;
import net.mcreator.terramity.procedures.PhantomAmuletEquippedTickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {
        MalwareWhileBaubleIsEquippedTickProcedure.class,
        PhantomAmuletEquippedTickProcedure.class
}, remap = false)
public abstract class MixinTerramityAuraScans {

    @Inject(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$serverSideOnly(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (CoOConfig.terramitySkipClientCurioScans && world != null && world.isClientSide()) {
            ci.cancel();
        }
    }
}
