package com.misanthropy.collections_of_optimizations.mixin.tonsofenchants;

import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.chest.Vitality;
import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.feet.HorseShoe;
import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.feet.LavaWalker;
import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.feet.Magnetize;
import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.feet.SwimmersBoots;
import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.head.FinalStand;
import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.head.Nutrition;
import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.head.enchNightVision;
import com.deltajay.tonsofenchants.enchantments.ArmorEnchantments.head.enchWaterBreathing;
import com.deltajay.tonsofenchants.enchantments.EEnchantments.EnhancedHandling;
import com.deltajay.tonsofenchants.enchantments.Enchantments.BlessedItem;
import com.deltajay.tonsofenchants.enchantments.Enchantments.Frostbite;
import com.deltajay.tonsofenchants.enchantments.Enchantments.PraiseTheSun;
import com.deltajay.tonsofenchants.enchantments.NegativeEnchantments.Decay;
import com.deltajay.tonsofenchants.enchantments.NegativeEnchantments.Hunger;
import com.deltajay.tonsofenchants.enchantments.NegativeEnchantments.Slowness;
import com.deltajay.tonsofenchants.enchantments.NegativeEnchantments.Spilling;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {
        Vitality.class,
        HorseShoe.class,
        LavaWalker.class,
        Magnetize.class,
        SwimmersBoots.class,
        enchNightVision.class,
        enchWaterBreathing.class,
        FinalStand.class,
        Nutrition.class,
        EnhancedHandling.class,
        BlessedItem.class,
        Frostbite.class,
        PraiseTheSun.class,
        Decay.class,
        Hunger.class,
        Slowness.class,
        Spilling.class
}, remap = false)
public abstract class MixinPlayerTickListeners {

    @Inject(
            method = "*(Lnet/minecraftforge/event/TickEvent$PlayerTickEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 0
    )
    private static void coo$singlePhasePlayerTick(TickEvent.PlayerTickEvent event, CallbackInfo ci) {
        if (CoOConfig.tonsofenchantsSinglePhasePlayerTick && event.phase != TickEvent.Phase.START) {
            ci.cancel();
        }
    }
}
