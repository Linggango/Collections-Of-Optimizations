package com.misanthropy.collections_of_optimizations.mixin.ftbchunks;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.map.MapChunk;
import dev.ftb.mods.ftbchunks.client.map.MapRegion;
import dev.ftb.mods.ftbchunks.client.map.MapRegionData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Collection;

@Mixin(value = MapRegionData.class, remap = false)
public abstract class MixinMapRegionData {

    @Shadow
    @Final
    public MapRegion region;

    @Shadow
    @Final
    public short[] height;

    @Shadow
    @Final
    public short[] waterLightAndBiome;

    @Shadow
    @Final
    public int[] foliage;

    @Shadow
    @Final
    public int[] grass;

    @Shadow
    @Final
    public int[] water;

    @Shadow
    public abstract int getBlockIndex(int index);

    @Invoker("writeData")
    abstract void coo$writeData(Collection<MapChunk> chunkList,
                                BufferedImage dataImage,
                                BufferedImage foliageImage,
                                BufferedImage grassImage,
                                BufferedImage waterImage,
                                BufferedImage blocksImage);

    @Inject(
            method = "write()V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$fastRegionWrite(CallbackInfo ci) {
        if (!CoOConfig.ftbchunksFastRegionWrite) {
            return;
        }

        Collection<MapChunk> chunkList;
        synchronized (this.region.dimension.getManager().lock) {
            chunkList = this.region.getModifiedChunks();
        }
        ci.cancel();
        if (chunkList.isEmpty()) {
            return;
        }

        BufferedImage dataImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        BufferedImage foliageImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
        BufferedImage grassImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
        BufferedImage waterImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
        BufferedImage blocksImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);

        int[] dataPixels = ((DataBufferInt) dataImage.getRaster().getDataBuffer()).getData();
        int[] foliagePixels = ((DataBufferInt) foliageImage.getRaster().getDataBuffer()).getData();
        int[] grassPixels = ((DataBufferInt) grassImage.getRaster().getDataBuffer()).getData();
        int[] waterPixels = ((DataBufferInt) waterImage.getRaster().getDataBuffer()).getData();
        int[] blocksPixels = ((DataBufferInt) blocksImage.getRaster().getDataBuffer()).getData();

        for (int index = 0; index < 262144; index++) {
            dataPixels[index] = ((this.height[index] & 0xFFFF) << 16) | (this.waterLightAndBiome[index] & 0xFFFF);
            foliagePixels[index] = this.foliage[index] & 0xFFFFFF;
            grassPixels[index] = this.grass[index] & 0xFFFFFF;
            waterPixels[index] = this.water[index] & 0xFFFFFF;
            blocksPixels[index] = this.getBlockIndex(index);
        }

        FTBChunksClient.MAP_EXECUTOR.execute(() -> {
            try {
                this.coo$writeData(chunkList, dataImage, foliageImage, grassImage, waterImage, blocksImage);
            } catch (Exception exception) {
                FTBChunks.LOGGER.error("Failed to write map region " + this.region.dimension + ":" + this.region + ":", exception);
            }
        });
    }
}
