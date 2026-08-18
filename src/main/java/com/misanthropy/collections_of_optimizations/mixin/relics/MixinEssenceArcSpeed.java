package com.misanthropy.collections_of_optimizations.mixin.relics;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.hurts.sskirillss.relics.entities.DeathEssenceEntity;
import it.hurts.sskirillss.relics.entities.LifeEssenceEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {DeathEssenceEntity.class, LifeEssenceEntity.class}, remap = false)
public abstract class MixinEssenceArcSpeed {

    @Inject(
            method = "moveTowardsTargetInArc",
            at = @At("TAIL"),
            require = 0,
            expect = 0
    )
    private void coo$clampArcSpeed(Entity target, CallbackInfo ci) {
        if (!CoOConfig.relicsClampEssenceSpeed) {
            return;
        }

        Entity self = (Entity) (Object) this;
        Vec3 motion = self.getDeltaMovement();
        double speed = motion.length();

        if (!Double.isFinite(speed)) {
            self.setDeltaMovement(Vec3.ZERO);

            return;
        }

        double cap = Math.min(CoOConfig.relicsEssenceMaxSpeed, self.distanceTo(target));

        if (speed > cap) {
            self.setDeltaMovement(motion.scale(cap / speed));
        }
    }
}
