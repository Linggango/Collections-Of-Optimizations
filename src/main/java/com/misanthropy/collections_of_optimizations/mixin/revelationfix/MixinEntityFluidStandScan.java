package com.misanthropy.collections_of_optimizations.mixin.revelationfix;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntityFluidStandScan {

    @Inject(
            method = "*(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getCommandSenderWorld()Lnet/minecraft/world/level/Level;"
            ),
            cancellable = true,
            require = 0,

            expect = 0
    )
    private void coo$skipMobFluidStandScan(Vec3 original, CallbackInfoReturnable<Vec3> cir) {
        if (CoOConfig.revelationfixSkipMobFluidStandScan && !((Object) this instanceof Player)) {
            cir.setReturnValue(original);
        }
    }
}
