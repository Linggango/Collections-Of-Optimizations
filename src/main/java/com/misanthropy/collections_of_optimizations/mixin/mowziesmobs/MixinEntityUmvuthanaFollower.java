package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollower;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.UUID;

@Mixin(value = EntityUmvuthanaFollower.class, remap = false)
public abstract class MixinEntityUmvuthanaFollower {

    @Shadow
    @Final
    private Class<?> leaderClass;

    @Shadow
    public abstract Optional<UUID> getLeaderUUID();

    @Unique
    private LivingEntity coo$resolvedLeader;

    @Inject(method = "getLeader", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$leaderFromCache(CallbackInfoReturnable<LivingEntity> cir) {
        if (!CoOConfig.mowziesmobsCacheUmvuthanaLeader) {
            return;
        }
        LivingEntity cached = this.coo$resolvedLeader;
        if (cached == null) {
            return;
        }
        EntityUmvuthanaFollower<?> self = (EntityUmvuthanaFollower<?>) (Object) this;
        Optional<UUID> uuid = this.getLeaderUUID();
        if (uuid.isEmpty()
                || cached.isRemoved()
                || cached.level() != self.level()
                || !this.leaderClass.isInstance(cached)
                || !uuid.get().equals(cached.getUUID())
                || !self.getBoundingBox().inflate(32.0, 32.0, 32.0).intersects(cached.getBoundingBox())) {
            this.coo$resolvedLeader = null;
            return;
        }
        cir.setReturnValue(cached);
    }

    @Inject(method = "getLeader", at = @At("RETURN"), require = 0)
    private void coo$rememberLeader(CallbackInfoReturnable<LivingEntity> cir) {
        if (CoOConfig.mowziesmobsCacheUmvuthanaLeader) {
            this.coo$resolvedLeader = cir.getReturnValue();
        }
    }
}
