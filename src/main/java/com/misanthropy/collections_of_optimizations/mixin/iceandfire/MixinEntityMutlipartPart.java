package com.misanthropy.collections_of_optimizations.mixin.iceandfire;

import com.github.alexthe666.iceandfire.entity.EntityMutlipartPart;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityMutlipartPart.class, remap = false)
public abstract class MixinEntityMutlipartPart {

    @Invoker("getScaleX")
    abstract float coo$scaleX();

    @Invoker("getScaleY")
    abstract float coo$scaleY();

    @Unique
    private int coo$lastCollideTick = -1;

    @Unique
    private EntityDimensions coo$cachedSize;

    @Unique
    private float coo$cachedWidth = Float.NaN;

    @Unique
    private float coo$cachedHeight = Float.NaN;

    @Inject(method = "collideWithNearbyEntities", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$dedupeCollideScan(CallbackInfo ci) {
        if (!CoOConfig.iceandfireLeanMultipartTick) {
            return;
        }
        int tick = ((EntityMutlipartPart) (Object) this).tickCount;
        if (this.coo$lastCollideTick == tick) {
            ci.cancel();
            return;
        }
        this.coo$lastCollideTick = tick;
    }

    @Inject(method = "m_6972_", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$reuseUnchangedSize(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (!CoOConfig.iceandfireLeanMultipartTick || this.coo$cachedSize == null) {
            return;
        }
        if (this.coo$scaleX() == this.coo$cachedWidth && this.coo$scaleY() == this.coo$cachedHeight) {
            cir.setReturnValue(this.coo$cachedSize);
        }
    }

    @Inject(method = "m_6972_", at = @At("RETURN"), require = 0)
    private void coo$rememberSize(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (!CoOConfig.iceandfireLeanMultipartTick) {
            return;
        }
        EntityDimensions resolved = cir.getReturnValue();
        if (resolved == null) {
            return;
        }
        this.coo$cachedSize = resolved;
        this.coo$cachedWidth = resolved.width;
        this.coo$cachedHeight = resolved.height;
    }
}
