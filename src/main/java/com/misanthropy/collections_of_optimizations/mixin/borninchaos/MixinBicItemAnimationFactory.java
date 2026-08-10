package com.misanthropy.collections_of_optimizations.mixin.borninchaos;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mcreator.borninchaosv.init.ItemAnimationFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoItem;

@Mixin(value = ItemAnimationFactory.class, remap = false)
public abstract class MixinBicItemAnimationFactory {

    @Inject(
            method = "animatedItems",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipUselessStackCopies(TickEvent.PlayerTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.borninchaosSkipItemAnimationCopies) {
            return;
        }

        if (event.phase != TickEvent.Phase.START) {
            ci.cancel();
            return;
        }

        Player player = event.player;
        if (!(player.getMainHandItem().getItem() instanceof GeoItem)
                && !(player.getOffhandItem().getItem() instanceof GeoItem)) {
            ci.cancel();
        }
    }
}
