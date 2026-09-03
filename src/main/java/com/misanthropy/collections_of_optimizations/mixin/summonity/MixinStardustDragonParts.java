package com.misanthropy.collections_of_optimizations.mixin.summonity;

import com.fevzi.summonity.entity.SummonedStardustDragonEntity;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = SummonedStardustDragonEntity.class, remap = false)
public abstract class MixinStardustDragonParts {

    @Unique
    private static final int COO$PARTS_RESCAN_TICKS = 5;

    @Unique
    private List<SummonedStardustDragonEntity> coo$parts;

    @Unique
    private ServerLevel coo$partsLevel;

    @Unique
    private int coo$partsStamp = Integer.MIN_VALUE;

    @Inject(method = "getDragonParts", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$answerFromPartsMemo(ServerLevel level, CallbackInfoReturnable<List<SummonedStardustDragonEntity>> cir) {
        if (!CoOConfig.summonityCacheDragonParts) {
            return;
        }
        List<SummonedStardustDragonEntity> parts = this.coo$parts;
        if (parts == null || this.coo$partsLevel != level) {
            return;
        }
        int age = ((Entity) (Object) this).tickCount - this.coo$partsStamp;
        if (age < 0 || age >= COO$PARTS_RESCAN_TICKS) {
            return;
        }
        for (SummonedStardustDragonEntity part : parts) {
            if (part.isRemoved() || part.level() != level) {
                return;
            }
        }
        cir.setReturnValue(parts);
    }

    @Inject(method = "getDragonParts", at = @At("RETURN"), require = 0)
    private void coo$rememberParts(ServerLevel level, CallbackInfoReturnable<List<SummonedStardustDragonEntity>> cir) {
        if (CoOConfig.summonityCacheDragonParts) {
            this.coo$parts = cir.getReturnValue();
            this.coo$partsLevel = level;
            this.coo$partsStamp = ((Entity) (Object) this).tickCount;
        }
    }

    @Inject(method = "spawnPart", at = @At("RETURN"), require = 0)
    private void coo$forgetPartsAfterSpawn(int partType, int index, CallbackInfo ci) {
        this.coo$parts = null;
    }
}
