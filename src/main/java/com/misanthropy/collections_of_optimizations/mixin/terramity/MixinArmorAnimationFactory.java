package com.misanthropy.collections_of_optimizations.mixin.terramity;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mcreator.terramity.init.ArmorAnimationFactory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoItem;

@Mixin(value = ArmorAnimationFactory.class, remap = false)
public abstract class MixinArmorAnimationFactory {

    @Inject(
            method = "animatedArmors",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipSlotRescan(TickEvent.PlayerTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.terramitySkipArmorAnimationScan) {
            return;
        }

        if (event.phase != TickEvent.Phase.END) {
            ci.cancel();
            return;
        }

        Player player = event.player;
        if (!(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof GeoItem)
                && !(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GeoItem)
                && !(player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof GeoItem)
                && !(player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof GeoItem)) {
            ci.cancel();
        }
    }
}
