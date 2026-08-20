package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.common.capability.CurioInventoryCapability;

import java.util.Collections;
import java.util.Map;

@Mixin(value = CurioInventoryCapability.CurioInventoryWrapper.class, remap = false)
public abstract class MixinCurioInventoryMapView {

    @Unique
    private Map<String, ICurioStacksHandler> coo$viewBacking;

    @Unique
    private Map<String, ICurioStacksHandler> coo$view;

    @Redirect(
            method = "getCurios",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Collections;unmodifiableMap(Ljava/util/Map;)Ljava/util/Map;"
            ),
            require = 0
    )
    private Map<String, ICurioStacksHandler> coo$reuseCurioMapView(Map<String, ICurioStacksHandler> backing) {
        if (!CoOConfig.curiosReuseCurioMapView || backing == null) {
            return Collections.unmodifiableMap(backing);
        }
        if (this.coo$viewBacking != backing) {
            this.coo$viewBacking = backing;
            this.coo$view = Collections.unmodifiableMap(backing);
        }
        return this.coo$view;
    }
}
