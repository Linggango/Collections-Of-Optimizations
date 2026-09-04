package com.misanthropy.collections_of_optimizations.mixin.cucumber;

import com.blakebr0.cucumber.helper.TileEntityHelper;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = TileEntityHelper.class, remap = false)
public class MixinCucumberTileDispatch {

    @Inject(
            method = "dispatchToNearbyPlayers(Lnet/minecraft/world/level/block/entity/BlockEntity;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$dispatchOnlyWhenWatched(BlockEntity tile, CallbackInfo ci) {
        if (!CoOConfig.cucumberLeanTileDispatch) {
            return;
        }

        var level = tile.getLevel();
        if (level == null) {
            ci.cancel();
            return;
        }

        List<? extends Player> players = level.players();
        var pos = tile.getBlockPos();
        double centreX = pos.getX() + 0.5D;
        double centreZ = pos.getZ() + 0.5D;

        Packet<?> packet = null;

        for (int i = 0, size = players.size(); i < size; i++) {
            if (!(players.get(i) instanceof ServerPlayer player)) {
                continue;
            }

            double dx = player.getX() - centreX;
            double dz = player.getZ() - centreZ;
            if (dx * dx + dz * dz >= 4096.0D) {
                continue;
            }

            if (packet == null) {
                packet = tile.getUpdatePacket();
                if (packet == null) {
                    break;
                }
            }

            player.connection.send(packet);
        }

        ci.cancel();
    }
}
