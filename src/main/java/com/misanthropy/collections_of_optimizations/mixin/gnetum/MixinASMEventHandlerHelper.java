package com.misanthropy.collections_of_optimizations.mixin.gnetum;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import me.decce.gnetum.ASMEventHandlerHelper;
import net.minecraftforge.eventbus.ASMEventHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ASMEventHandlerHelper.class, remap = false)
public abstract class MixinASMEventHandlerHelper {

    @Shadow
    @Final
    private static Reference2ObjectMap<ASMEventHandler, String> mapModId;

    @Inject(
            method = "tryGetModId(Lnet/minecraftforge/eventbus/ASMEventHandler;)Ljava/lang/String;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$singleModIdLookup(ASMEventHandler handler, CallbackInfoReturnable<String> cir) {
        if (!CoOConfig.gnetumSingleModIdLookup) {
            return;
        }

        String modId = mapModId.get(handler);
        if (modId != null) {
            cir.setReturnValue(modId);
        }
    }
}
