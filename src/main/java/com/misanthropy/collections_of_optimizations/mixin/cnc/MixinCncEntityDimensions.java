package com.misanthropy.collections_of_optimizations.mixin.cnc;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ModEntityFilter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinCncEntityDimensions {

    @Inject(
            method = "refreshDimensions",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipUnchangedRefresh(CallbackInfo ci) {
        if (!CoOConfig.cncSkipRedundantDimensionRefresh) {
            return;
        }

        Entity self = (Entity) (Object) this;
        if (!ModEntityFilter.CRITTERS_N_CRAWLERS.matches(self)) {
            return;
        }

        EntityDimensions wanted = self.getDimensions(self.getPose());
        if (wanted.width == self.getBbWidth() && wanted.height == self.getBbHeight()) {
            ci.cancel();
        }
    }
}
