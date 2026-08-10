package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(MultiBufferSource.BufferSource.class)
public interface BufferSourceFixedBuffersAccessor {

    @Accessor("fixedBuffers")
    Map<RenderType, BufferBuilder> coo$fixedBuffers();
}
