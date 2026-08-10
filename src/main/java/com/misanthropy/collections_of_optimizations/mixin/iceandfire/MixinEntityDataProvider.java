package com.misanthropy.collections_of_optimizations.mixin.iceandfire;

import com.github.alexthe666.iceandfire.entity.props.EntityData;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.IafEntityDataHolder;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityDataProvider.class, remap = false)
public abstract class MixinEntityDataProvider {

    @Inject(
            method = "getCapability(Lnet/minecraft/world/entity/Entity;)Lnet/minecraftforge/common/util/LazyOptional;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$entityDataFromEntity(Entity entity, CallbackInfoReturnable<LazyOptional<EntityData>> cir) {
        if (!CoOConfig.iceandfireFastEntityDataLookup || !(entity instanceof IafEntityDataHolder holder)) {
            return;
        }
        @SuppressWarnings("unchecked")
        LazyOptional<EntityData> cached = (LazyOptional<EntityData>) holder.coo$getIafEntityData();
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }

    @Inject(
            method = "getCapability(Lnet/minecraft/world/entity/Entity;)Lnet/minecraftforge/common/util/LazyOptional;",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$rememberEntityData(Entity entity, CallbackInfoReturnable<LazyOptional<EntityData>> cir) {
        if (!CoOConfig.iceandfireFastEntityDataLookup || !(entity instanceof IafEntityDataHolder holder)) {
            return;
        }
        if (holder.coo$getIafEntityData() != null) {
            return;
        }
        LazyOptional<EntityData> resolved = cir.getReturnValue();
        if (resolved == null || !resolved.isPresent()) {
            return;
        }
        holder.coo$setIafEntityData(resolved);
        resolved.addListener(ignored -> holder.coo$setIafEntityData(null));
    }
}
