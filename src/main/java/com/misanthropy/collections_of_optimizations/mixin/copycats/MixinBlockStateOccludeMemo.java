package com.misanthropy.collections_of_optimizations.mixin.copycats;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockBehaviour.BlockStateBase.class, priority = 1500)
public abstract class MixinBlockStateOccludeMemo {

    @Unique
    private byte coo$occludeMemo;

    @Inject(method = "canOcclude", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$serveOccludeMemo(CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.copycatsMemoStateOcclusion) {
            return;
        }
        byte memo = this.coo$occludeMemo;
        if (memo != 0) {
            cir.setReturnValue(memo == 1);
        }
    }

    @Inject(method = "canOcclude", at = @At("RETURN"), require = 0)
    private void coo$storeOccludeMemo(CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.copycatsMemoStateOcclusion && this.coo$occludeMemo == 0) {
            this.coo$occludeMemo = (byte) (cir.getReturnValueZ() ? 1 : 2);
        }
    }
}
