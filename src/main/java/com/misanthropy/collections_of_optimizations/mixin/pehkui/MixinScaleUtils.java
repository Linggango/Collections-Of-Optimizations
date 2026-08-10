package com.misanthropy.collections_of_optimizations.mixin.pehkui;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.InteractionBoxScaleHolder;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleEventCallback;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.util.ScaleUtils;

import java.util.Collection;

@Mixin(value = ScaleUtils.class, remap = false)
public abstract class MixinScaleUtils {

    @Inject(method = "tickScale", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$leanTickScale(ScaleData data, CallbackInfo ci) {
        if (!CoOConfig.pehkuiLeanScaleTick) {
            return;
        }

        ScaleType type = data.getScaleType();

        Collection<ScaleEventCallback> pre = type.getPreTickEvent();
        if (!pre.isEmpty()) {
            for (ScaleEventCallback callback : pre) {
                callback.onEvent(data);
            }
        }

        data.tick();

        Collection<ScaleEventCallback> post = type.getPostTickEvent();
        if (!post.isEmpty()) {
            for (ScaleEventCallback callback : post) {
                callback.onEvent(data);
            }
        }

        ci.cancel();
    }

    @WrapMethod(method = "getInteractionBoxWidthScale(Lnet/minecraft/world/entity/Entity;)F", require = 0)
    private static float coo$memoBoxWidthScale(Entity entity, Operation<Float> original) {
        if (!CoOConfig.pehkuiMemoInteractionBoxScales || entity == null) {
            return original.call(entity);
        }
        InteractionBoxScaleHolder holder = (InteractionBoxScaleHolder) entity;
        int stamp = entity.tickCount;
        if (holder.coo$boxWidthStamp() == stamp) {
            return holder.coo$boxWidthScale();
        }
        float width = original.call(entity);
        holder.coo$storeBoxWidthScale(stamp, width);
        return width;
    }

    @WrapMethod(method = "getInteractionBoxHeightScale(Lnet/minecraft/world/entity/Entity;)F", require = 0)
    private static float coo$memoBoxHeightScale(Entity entity, Operation<Float> original) {
        if (!CoOConfig.pehkuiMemoInteractionBoxScales || entity == null) {
            return original.call(entity);
        }
        InteractionBoxScaleHolder holder = (InteractionBoxScaleHolder) entity;
        int stamp = entity.tickCount;
        if (holder.coo$boxHeightStamp() == stamp) {
            return holder.coo$boxHeightScale();
        }
        float height = original.call(entity);
        holder.coo$storeBoxHeightScale(stamp, height);
        return height;
    }
}
