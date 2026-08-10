package com.misanthropy.collections_of_optimizations.mixin.structurify;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.StructurifyConfigMirror;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.StructureAccess;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = StructureManager.class, priority = 1500)
public abstract class MixinStructureManagerStartLookup {

    @Inject(
            method = "getStartForStructure",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$plainStartLookup(SectionPos sectionPos,
                                      Structure structure,
                                      StructureAccess structureAccess,
                                      CallbackInfoReturnable<StructureStart> cir) {
        if (!CoOConfig.structurifySkipStartCheckWrap) {
            return;
        }
        if (StructurifyConfigMirror.structureChecksLive()
                || !StructurifyConfigMirror.allStructureChecksDisabled()) {
            return;
        }
        cir.setReturnValue(structureAccess.getStartForStructure(structure));
    }
}
