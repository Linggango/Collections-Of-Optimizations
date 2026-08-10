package com.misanthropy.collections_of_optimizations.mixin.alexsmobs;

import com.github.alexthe666.alexsmobs.event.ServerEvents;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerEvents.class, remap = false)
public abstract class MixinAlexsMobsServerEvents {

    @Inject(
            method = "onEntityFinalizeSpawn",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipCreeperAvoidGoals(MobSpawnEvent.FinalizeSpawn event, CallbackInfo ci) {
        if (CoOConfig.alexsmobsSkipCreeperAvoidGoals && event.getEntity() instanceof Creeper) {
            ci.cancel();
        }
    }

    @ModifyArg(
            method = "onEntityFinalizeSpawn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/target/NearestAttackableTargetGoal;<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V"
            ),
            index = 2,
            require = 0
    )
    private int coo$throttleSpiderFlyScan(int randomInterval) {
        if (randomInterval != 1) {
            return randomInterval;
        }
        int configured = CoOConfig.alexsmobsSpiderFlyScanInterval;
        return configured > 0 ? configured : randomInterval;
    }
}
