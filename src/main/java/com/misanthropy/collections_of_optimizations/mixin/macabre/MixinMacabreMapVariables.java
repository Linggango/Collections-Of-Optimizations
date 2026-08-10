package com.misanthropy.collections_of_optimizations.mixin.macabre;

import com.curseforge.macabre.network.MacabreModVariables;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.MacabreSyncQueue;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MacabreModVariables.MapVariables.class, remap = false)
public abstract class MixinMacabreMapVariables {

    @WrapWithCondition(
            method = "syncData",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/network/simple/SimpleChannel;send(Lnet/minecraftforge/network/PacketDistributor$PacketTarget;Ljava/lang/Object;)V"
            ),
            require = 0
    )
    private boolean coo$deferWorldVariableSync(SimpleChannel channel, PacketDistributor.PacketTarget target, Object message) {
        if (!CoOConfig.macabreCoalesceVariableSync) {
            return true;
        }
        return !MacabreSyncQueue.queue(this, channel, target, message);
    }
}
