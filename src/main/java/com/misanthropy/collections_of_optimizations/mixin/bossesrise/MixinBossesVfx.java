package com.misanthropy.collections_of_optimizations.mixin.bossesrise;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BossEntityScan;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.unusual.blockfactorysbosses.procedures.BossesVFXProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BossesVFXProcedure.class, remap = false)
public abstract class MixinBossesVfx {

    @WrapOperation(
            method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;D)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;entitiesForRendering()Ljava/lang/Iterable;"
            ),
            remap = true,
            require = 0
    )
    private static Iterable<Entity> coo$onlyWalkBossesRiseEntities(ClientLevel level,
                                                                   Operation<Iterable<Entity>> original) {
        if (!CoOConfig.bossesriseLeanVfxScan) {
            return original.call(level);
        }
        return BossEntityScan.matches(level);
    }
}
