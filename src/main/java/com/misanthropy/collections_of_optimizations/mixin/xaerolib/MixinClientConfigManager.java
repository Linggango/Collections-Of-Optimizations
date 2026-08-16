package com.misanthropy.collections_of_optimizations.mixin.xaerolib;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ClientTickStamp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.config.option.BuiltInProfiledConfigOptions;
import xaero.lib.common.config.option.ConfigOption;
import xaero.lib.common.config.profile.ConfigProfile;

@Mixin(value = ClientConfigManager.class, remap = false)
public abstract class MixinClientConfigManager {

    @Unique
    private ConfigProfile coo$profile;

    @Unique
    private int coo$profileStamp;

    @Unique
    private boolean coo$profileValid;

    @Unique
    private ConfigProfile coo$enforcementProfile;

    @Unique
    private int coo$enforcementStamp;

    @Unique
    private boolean coo$enforcementValid;

    @Unique
    private boolean coo$enforcementResult;

    @Inject(
            method = "getCurrentProfile()Lxaero/lib/common/config/profile/ConfigProfile;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$serveCachedProfile(CallbackInfoReturnable<ConfigProfile> cir) {
        if (!CoOConfig.xaerolibCacheConfigProfile || !this.coo$profileValid) {
            return;
        }
        int stamp = ClientTickStamp.currentOnRenderThread();
        if (stamp >= 0 && stamp == this.coo$profileStamp) {
            cir.setReturnValue(this.coo$profile);
        }
    }

    @Inject(
            method = "getCurrentProfile()Lxaero/lib/common/config/profile/ConfigProfile;",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$rememberProfile(CallbackInfoReturnable<ConfigProfile> cir) {
        if (!CoOConfig.xaerolibCacheConfigProfile) {
            return;
        }
        int stamp = ClientTickStamp.currentOnRenderThread();
        if (stamp < 0) {
            return;
        }
        this.coo$profile = cir.getReturnValue();
        this.coo$profileStamp = stamp;
        this.coo$profileValid = true;
    }

    @Inject(
            method = "shouldIgnoreServerEnforcement(Lxaero/lib/common/config/profile/ConfigProfile;Lxaero/lib/common/config/option/ConfigOption;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$serveCachedEnforcement(ConfigProfile config, ConfigOption<?> option,
                                            CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.xaerolibCacheEnforcementCheck
                || !this.coo$enforcementValid
                || this.coo$enforcementProfile != config
                || option == BuiltInProfiledConfigOptions.IGNORE_ENFORCEMENT_IF_EDITOR) {
            return;
        }
        int stamp = ClientTickStamp.currentOnRenderThread();
        if (stamp >= 0 && stamp == this.coo$enforcementStamp) {
            cir.setReturnValue(this.coo$enforcementResult);
        }
    }

    @Inject(
            method = "shouldIgnoreServerEnforcement(Lxaero/lib/common/config/profile/ConfigProfile;Lxaero/lib/common/config/option/ConfigOption;)Z",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$rememberEnforcement(ConfigProfile config, ConfigOption<?> option,
                                         CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.xaerolibCacheEnforcementCheck
                || option == BuiltInProfiledConfigOptions.IGNORE_ENFORCEMENT_IF_EDITOR) {
            return;
        }
        int stamp = ClientTickStamp.currentOnRenderThread();
        if (stamp < 0) {
            return;
        }
        this.coo$enforcementProfile = config;
        this.coo$enforcementResult = cir.getReturnValueZ();
        this.coo$enforcementStamp = stamp;
        this.coo$enforcementValid = true;
    }
}
