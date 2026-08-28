package com.misanthropy.collections_of_optimizations.mixin.createsolar;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraftforge.client.event.RenderGuiEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;

@Mixin(targets = "com.hachirouwu.createsolar.ClientIntegration", remap = false)
public abstract class MixinCreateSolarClientIntegration {

    @Unique
    private static Class<?> coo$probedOwner;

    @Unique
    private static Method coo$probedMethod;

    @Unique
    private static boolean coo$probeFailed;

    @Inject(method = "onRenderGui", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipDeadOverlay(RenderGuiEvent.Post event, CallbackInfo ci) {
        if (CoOConfig.createsolarMemoGogglesLookup && coo$probeFailed) {
            ci.cancel();
        }
    }

    @WrapOperation(
            method = "onRenderGui",
            at = @At(value = "INVOKE", target = "Ljava/lang/Class;getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"),
            require = 0
    )
    private static Method coo$memoGogglesLookup(Class<?> owner, String name, Class<?>[] parameters, Operation<Method> original) {
        if (!CoOConfig.createsolarMemoGogglesLookup) {
            return original.call(owner, name, parameters);
        }

        if (coo$probedOwner != owner) {
            coo$probedOwner = owner;
            coo$probedMethod = null;
            try {
                coo$probedMethod = original.call(owner, name, parameters);
            } catch (Throwable ignored) {
            }
            coo$probeFailed = coo$probedMethod == null;
        }

        if (coo$probedMethod == null) {
            throw new IllegalStateException("collections_of_optimizations: createsolar goggles probe failed once, not retrying");
        }
        return coo$probedMethod;
    }
}
