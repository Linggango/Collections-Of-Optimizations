package com.misanthropy.collections_of_optimizations.mixin.iceandfire;

import com.github.alexthe666.iceandfire.client.render.entity.RenderDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = RenderDragonBase.class, remap = false)
public abstract class MixinRenderDragonBase {

    @Unique
    private Map<String, ResourceLocation[]> coo$dragonTextures;

    @Unique
    private static int coo$packDragonState(EntityDragonBase dragon) {
        int stage = dragon.getDragonStage();
        if (stage < 0 || stage > 7) {
            return -1;
        }
        return stage << 5
                | (dragon.isModelDead() ? 16 : 0)
                | (dragon.isMale() ? 8 : 0)
                | (dragon.isSkeletal() ? 4 : 0)
                | (dragon.isSleeping() ? 2 : 0)
                | (dragon.isBlinking() ? 1 : 0);
    }

    @Inject(method = "getTextureLocation", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$dragonTextureFromPackedKey(EntityDragonBase dragon, CallbackInfoReturnable<ResourceLocation> cir) {
        if (!CoOConfig.iceandfireCacheDragonTexture || this.coo$dragonTextures == null) {
            return;
        }
        int index = coo$packDragonState(dragon);
        if (index < 0) {
            return;
        }
        ResourceLocation[] slots = this.coo$dragonTextures.get(dragon.getVariantName(dragon.getVariant()));
        if (slots == null) {
            return;
        }
        ResourceLocation cached = slots[index];
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }

    @Inject(method = "getTextureLocation", at = @At("RETURN"), require = 0)
    private void coo$rememberDragonTexture(EntityDragonBase dragon, CallbackInfoReturnable<ResourceLocation> cir) {
        if (!CoOConfig.iceandfireCacheDragonTexture) {
            return;
        }
        ResourceLocation resolved = cir.getReturnValue();
        if (resolved == null) {
            return;
        }
        int index = coo$packDragonState(dragon);
        if (index < 0) {
            return;
        }
        if (this.coo$dragonTextures == null) {
            this.coo$dragonTextures = new HashMap<>();
        }
        this.coo$dragonTextures
                .computeIfAbsent(dragon.getVariantName(dragon.getVariant()), key -> new ResourceLocation[256])[index] = resolved;
    }
}
