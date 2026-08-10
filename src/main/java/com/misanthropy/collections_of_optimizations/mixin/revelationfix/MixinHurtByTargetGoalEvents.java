package com.misanthropy.collections_of_optimizations.mixin.revelationfix;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Spider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HurtByTargetGoal.class)
public abstract class MixinHurtByTargetGoalEvents extends TargetGoal {

    MixinHurtByTargetGoalEvents(Mob mob, boolean mustSee) {
        super(mob, mustSee);
    }

    @Inject(
            method = "*(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V",
            at = @At(
                    value = "NEW",
                    target = "com/mega/revelationfix/api/event/entity/LivingHurtByTargetGoalEvent$CanUse",
                    remap = false
            ),
            cancellable = true,
            require = 0,
            expect = 0,
            remap = false
    )
    private void coo$skipNonSpiderHurtByTargetEvent(CallbackInfoReturnable<Boolean> targetCir, CallbackInfo ci) {
        if (CoOConfig.revelationfixSkipNonSpiderHurtByTargetEvents && !(this.mob instanceof Spider)) {
            ci.cancel();
        }
    }
}
