package com.misanthropy.collections_of_optimizations.mixin.mysticalagriculture;

import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Crop.class, remap = false)
public abstract class MixinMaCrop {

    @Shadow
    private Component displayName;

    @Shadow
    public abstract String getModId();

    @Shadow
    public abstract String getName();

    @Unique
    private String coo$nameKey;

    @Inject(
            method = "getDisplayName()Lnet/minecraft/network/chat/Component;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$memoNameKey(CallbackInfoReturnable<Component> cir) {
        if (!CoOConfig.mysticalagricultureMemoCropNameKey || this.displayName != null) {
            return;
        }

        String key = this.coo$nameKey;
        if (key == null) {
            key = "crop." + this.getModId() + "." + this.getName();
            this.coo$nameKey = key;
        }

        cir.setReturnValue(Component.translatable(key));
    }
}
