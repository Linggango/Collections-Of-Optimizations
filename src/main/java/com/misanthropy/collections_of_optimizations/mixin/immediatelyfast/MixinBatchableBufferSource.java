package com.misanthropy.collections_of_optimizations.mixin.immediatelyfast;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.vanilla.BufferSourceFixedBuffersAccessor;
import com.mojang.blaze3d.vertex.BufferBuilder;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import net.minecraft.client.renderer.RenderType;
import net.raphimc.immediatelyfast.feature.core.BatchableBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Mixin(value = BatchableBufferSource.class, remap = false)
public abstract class MixinBatchableBufferSource {

    @Shadow
    protected Map<RenderType, ReferenceSet<BufferBuilder>> fallbackBuffers;

    @Shadow
    protected abstract BufferBuilder addNewFallbackBuffer(RenderType layer);

    @Inject(
            method = "getOrCreateBufferBuilder",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$singleLookupGetOrCreate(RenderType layer, CallbackInfoReturnable<BufferBuilder> cir) {
        if (!CoOConfig.immediatelyfastSingleBufferLookup) {
            return;
        }
        if (!layer.canConsolidateConsecutiveGeometry()) {
            cir.setReturnValue(this.addNewFallbackBuffer(layer));
            return;
        }
        BufferBuilder fixed = ((BufferSourceFixedBuffersAccessor) this).coo$fixedBuffers().get(layer);
        if (fixed != null) {
            cir.setReturnValue(fixed);
            return;
        }
        ReferenceSet<BufferBuilder> fallback = this.fallbackBuffers.get(layer);
        cir.setReturnValue(fallback != null ? fallback.iterator().next() : this.addNewFallbackBuffer(layer));
    }

    @Inject(
            method = "getBufferBuilder",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$singleLookupGetBuffer(RenderType layer, CallbackInfoReturnable<Set<BufferBuilder>> cir) {
        if (!CoOConfig.immediatelyfastSingleBufferLookup) {
            return;
        }
        ReferenceSet<BufferBuilder> fallback = this.fallbackBuffers.get(layer);
        if (fallback != null) {
            cir.setReturnValue(fallback);
            return;
        }
        BufferBuilder fixed = ((BufferSourceFixedBuffersAccessor) this).coo$fixedBuffers().get(layer);
        cir.setReturnValue(fixed != null ? Collections.singleton(fixed) : Collections.emptySet());
    }
}
