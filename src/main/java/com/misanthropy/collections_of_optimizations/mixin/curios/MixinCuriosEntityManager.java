package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.core.CuriosSlotCache;
import net.minecraft.nbt.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.common.data.CuriosEntityManager;

@Mixin(value = CuriosEntityManager.class, remap = false)
public abstract class MixinCuriosEntityManager {

    @Inject(method = "applySyncPacket(Lnet/minecraft/nbt/ListTag;)V", at = @At("RETURN"), require = 0)
    private static void coo$invalidateSlotCache(ListTag tag, CallbackInfo ci) {
        CuriosSlotCache.invalidate();
    }
}
