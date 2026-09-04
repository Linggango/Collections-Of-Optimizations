package com.misanthropy.collections_of_optimizations.mixin.mysticalagriculture;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import com.blakebr0.mysticalagriculture.api.crafting.ISoulExtractionRecipe;
import com.blakebr0.mysticalagriculture.tileentity.SoulExtractorTileEntity;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SoulExtractorTileEntity.class, remap = false)
public abstract class MixinMaSoulExtractor {

    @Shadow
    @Final
    private BaseItemStackHandler inventory;

    @Inject(
            method = "getActiveRecipe()Lcom/blakebr0/mysticalagriculture/api/crafting/ISoulExtractionRecipe;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipEmptyInputScan(CallbackInfoReturnable<ISoulExtractionRecipe> cir) {
        if (!CoOConfig.mysticalagricultureSkipIdleSoulExtractor) {
            return;
        }

        if (this.inventory.getStackInSlot(0).isEmpty()) {
            cir.setReturnValue(null);
        }
    }
}
