package com.misanthropy.collections_of_optimizations.mixin.goetydelight;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.GoetyDelightLogItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.v_black_cat.goetydelight.init.CraftingDisplayHandler;
import net.v_black_cat.goetydelight.recipe.RecraftBoatPlate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = {RecraftBoatPlate.class, CraftingDisplayHandler.class}, remap = false)
public abstract class MixinLogItemFilter {

    @Inject(
            method = "getLogItems()Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$cachedLogItems(CallbackInfoReturnable<List<Item>> cir,
                                           @Share("coo$logGeneration") LocalIntRef generation) {
        if (!CoOConfig.goetydelightCacheLogItems) {
            return;
        }

        List<Item> cached = GoetyDelightLogItems.peek();
        if (cached != null) {
            cir.setReturnValue(cached);
            return;
        }

        generation.set(GoetyDelightLogItems.generation());
    }

    @Inject(
            method = "getLogItems()Ljava/util/List;",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$storeLogItems(CallbackInfoReturnable<List<Item>> cir,
                                          @Share("coo$logGeneration") LocalIntRef generation) {
        if (CoOConfig.goetydelightCacheLogItems) {
            GoetyDelightLogItems.store(generation.get(), cir.getReturnValue());
        }
    }

    @Inject(
            method = "lambda$getLogItems$0(Lnet/minecraft/world/item/Item;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$leanLogScan(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.goetydelightLeanLogScan) {
            return;
        }

        cir.setReturnValue(item.builtInRegistryHolder().is(ItemTags.LOGS));
    }
}
