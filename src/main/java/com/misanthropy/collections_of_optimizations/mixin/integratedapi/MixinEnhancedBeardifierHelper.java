package com.misanthropy.collections_of_optimizations.mixin.integratedapi;

import com.craisinlord.integrated_api.world.terrainadaptation.beardifier.EnhancedBeardifierData;
import com.craisinlord.integrated_api.world.terrainadaptation.beardifier.EnhancedBeardifierHelper;
import com.craisinlord.integrated_api.world.terrainadaptation.beardifier.EnhancedBeardifierRigid;
import com.craisinlord.integrated_api.world.terrainadaptation.beardifier.EnhancedJigsawJunction;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EnhancedBeardifierHelper.class, remap = false)
public abstract class MixinEnhancedBeardifierHelper {

    @Inject(
            method = "computeDensity",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipEmptyAdaptation(
            DensityFunction.FunctionContext ctx,
            double density,
            EnhancedBeardifierData data,
            CallbackInfoReturnable<Double> cir) {
        if (!CoOConfig.integratedapiSkipEmptyBeardifier || data == null) {
            return;
        }
        ObjectListIterator<EnhancedBeardifierRigid> rigids = data.getEnhancedRigidIterator();
        if (rigids == null || rigids.hasNext()) {
            return;
        }
        ObjectListIterator<EnhancedJigsawJunction> junctions = data.getEnhancedJunctionIterator();
        if (junctions == null || junctions.hasNext()) {
            return;
        }
        cir.setReturnValue(density);
    }
}
