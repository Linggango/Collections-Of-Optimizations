package com.misanthropy.collections_of_optimizations.mixin.mysticalagriculture;

import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.util.AugmentUtils;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.MaAugmentKeys;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = AugmentUtils.class, remap = false)
public class MixinMaAugmentUtils {

    @Inject(
            method = "getAugments(Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$leanAugmentScan(ItemStack stack, CallbackInfoReturnable<List<Augment>> cir) {
        if (!CoOConfig.mysticalagricultureLeanAugmentLookup) {
            return;
        }

        List<Augment> augments = new ArrayList<>();
        MaAugmentKeys.collect(stack, augments);

        cir.setReturnValue(augments);
    }

    @Inject(
            method = "getArmorAugments(Lnet/minecraft/world/entity/player/Player;)Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$leanArmorAugmentScan(Player player, CallbackInfoReturnable<List<Augment>> cir) {
        if (!CoOConfig.mysticalagricultureLeanAugmentLookup) {
            return;
        }

        List<Augment> augments = new ArrayList<>();
        var armor = player.getInventory().armor;

        for (int i = 0, size = armor.size(); i < size; i++) {
            MaAugmentKeys.collect(armor.get(i), augments);
        }

        cir.setReturnValue(augments);
    }
}
