package com.misanthropy.collections_of_optimizations.mixin.cnc;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = {
        "net.imasillylittleguy.cnc.procedures.BeaverAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.BlackbearAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.CaribouadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.ChupacabraadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.CougaradvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.CoyoteadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.ElkadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.GiantBoaradvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.GooseAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.GreywolfadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.HarmlessSnakeAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.HowlerAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.MarmotAdvanceProcProcedure",
        "net.imasillylittleguy.cnc.procedures.MouseAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.PitViperAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.PlayerSquonkCryEffectProcedure",
        "net.imasillylittleguy.cnc.procedures.RattlesnakeAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.RingtailadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.SasquatchAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.SkinwalkerAdvanceProcProcedure",
        "net.imasillylittleguy.cnc.procedures.SkunkAdvanceProcProcedure",
        "net.imasillylittleguy.cnc.procedures.SquonkAdvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.TurkeyadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.WechugeadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.WendigoadvanceprocProcedure",
        "net.imasillylittleguy.cnc.procedures.WhitetailAdvanceProcProcedure",
        "net.imasillylittleguy.cnc.procedures.WolverineadvanceprocProcedure"
}, remap = false)
public abstract class MixinCncEncounterProcedures {

    @Inject(
            method = "onPlayerTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$serverSideAndThrottled(TickEvent.PlayerTickEvent event, CallbackInfo ci) {
        if (event == null || event.player == null) {
            return;
        }
        if (CoOConfig.cncSkipClientEncounterScans && event.player.level().isClientSide()) {
            ci.cancel();
            return;
        }
        int interval = CoOConfig.cncEncounterScanInterval;
        if (interval > 1 && Math.floorMod(event.player.tickCount, interval) != 0) {
            ci.cancel();
        }
    }
}
