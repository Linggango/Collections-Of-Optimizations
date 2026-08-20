package com.misanthropy.collections_of_optimizations.mixin.copycats;

import com.copycatsplus.copycats.utility.BlockEntityUtils;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockEntityUtils.class, remap = false)
public abstract class MixinBlockEntityUtilsVirtualCheck {

    @Unique
    private static Class<?> coo$virtualWorldClass;

    @Unique
    private static boolean coo$virtualWorldResolved;

    @Inject(method = "isWorldRenderWorld", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$leanVirtualCheck(Level level, CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.copycatsLeanVirtualWorldCheck) {
            return;
        }
        if (!coo$virtualWorldResolved) {
            coo$virtualWorldResolved = true;
            if (FMLEnvironment.dist == Dist.CLIENT) {
                try {
                    coo$virtualWorldClass = Class.forName("com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld");
                } catch (Throwable ignored) {
                }
            }
        }
        if (FMLEnvironment.dist == Dist.CLIENT && coo$virtualWorldClass == null) {
            return;
        }
        cir.setReturnValue(coo$virtualWorldClass != null && coo$virtualWorldClass.isInstance(level));
    }
}
