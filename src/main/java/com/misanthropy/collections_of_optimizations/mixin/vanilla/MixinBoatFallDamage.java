package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public abstract class MixinBoatFallDamage {

    @Shadow
    private double lastYd;

    @Inject(method = "checkFallDamage", at = @At("HEAD"), cancellable = true, require = 0)
    protected void coo$skipBoatFallDamage(double deltaY, boolean onGround, BlockState state, BlockPos pos, CallbackInfo ci) {
        if (!CoOConfig.vanillaFixBoatFallDamage) {
            return;
        }
        Boat boat = (Boat) (Object) this;
        this.lastYd = boat.getDeltaMovement().y;
        if (!boat.isPassenger()) {
            if (onGround) {
                boat.resetFallDistance();
            } else if (deltaY < 0.0D && !boat.canBoatInFluid(boat.level().getFluidState(boat.blockPosition().below()))) {
                boat.fallDistance -= (float) deltaY;
            }
        }
        ci.cancel();
    }
}
