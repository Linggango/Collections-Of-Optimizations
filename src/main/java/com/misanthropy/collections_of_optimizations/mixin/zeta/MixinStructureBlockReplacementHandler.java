package com.misanthropy.collections_of_optimizations.mixin.zeta;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ZetaStructureFunctions;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.violetmoon.zeta.util.handler.StructureBlockReplacementHandler;
import org.violetmoon.zeta.util.handler.StructureBlockReplacementHandler.StructureFunction;
import org.violetmoon.zeta.util.handler.StructureBlockReplacementHandler.StructureHolder;

@Mixin(value = StructureBlockReplacementHandler.class, remap = false)
public abstract class MixinStructureBlockReplacementHandler {

    @Inject(method = "getResultingBlockState", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$leanStructureReplacement(
            ServerLevelAccessor level, BlockState blockstate, CallbackInfoReturnable<BlockState> cir) {
        if (!CoOConfig.zetaLeanStructureReplacement) {
            return;
        }

        StructureHolder curr = ZetaStructureReplacementAccessor.coo$currentStructureHolder();
        if (curr != null && curr.currentStructure != null) {
            StructureFunction[] functions = ZetaStructureFunctions.snapshot(ZetaStructureReplacementAccessor.coo$functions());
            for (StructureFunction fun : functions) {
                BlockState res = fun.transformBlockstate(level, blockstate, curr);
                if (res != null) {
                    cir.setReturnValue(res);
                    return;
                }
            }
        }
        cir.setReturnValue(blockstate);
    }
}
