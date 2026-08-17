package com.misanthropy.collections_of_optimizations.mixin.amendments;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mehvahdjukaar.amendments.common.block.WallLanternBlock;
import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.Message;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = WallLanternBlock.class, remap = false)
public abstract class MixinWallLanternSway {

    @WrapWithCondition(
            method = "m_7892_",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/mehvahdjukaar/moonlight/api/platform/network/ChannelHandler;sentToAllClientPlayersTrackingEntity(Lnet/minecraft/world/entity/Entity;Lnet/mehvahdjukaar/moonlight/api/platform/network/Message;)V"
            ),
            require = 0
    )
    private boolean coo$skipIdleSwaySync(ChannelHandler channel, Entity entity, Message message) {
        if (!CoOConfig.amendmentsSkipIdleSwaySync) {
            return true;
        }
        return entity.xo != entity.getX() || entity.yo != entity.getY() || entity.zo != entity.getZ();
    }
}
