package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VecDeltaCodec.class)
public abstract class MixinVecDeltaCodec {

    @Shadow
    private Vec3 base;

    @Inject(method = "delta", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipZeroDelta(Vec3 position, CallbackInfoReturnable<Vec3> cir) {
        if (!CoOConfig.vanillaLeanTrackerDelta) {
            return;
        }
        Vec3 base = this.base;

        if (position.x == base.x && position.y == base.y && position.z == base.z) {
            cir.setReturnValue(Vec3.ZERO);
        }
    }
}
