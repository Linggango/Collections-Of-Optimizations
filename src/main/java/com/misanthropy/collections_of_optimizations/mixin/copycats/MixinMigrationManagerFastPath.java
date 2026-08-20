package com.misanthropy.collections_of_optimizations.mixin.copycats;

import com.copycatsplus.copycats.foundation.copycat.CCCopycatBlock;
import com.copycatsplus.copycats.foundation.copycat.MigrationManager;
import com.copycatsplus.copycats.foundation.copycat.multistate.MultiStateCopycatBlock;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MigrationManager.class, remap = false)
public abstract class MixinMigrationManagerFastPath {

    @Unique
    private static BlockEntityType<?> coo$createCopycatType;

    @Unique
    private static boolean coo$typeResolved;

    @Inject(method = "migrateBlockEntity", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$fastSkipBlockEntity(LevelChunk chunk, BlockEntity blockEntity, CallbackInfoReturnable<BlockEntity> cir) {
        if (!CoOConfig.copycatsFastMigrationChecks) {
            return;
        }
        if (!coo$typeResolved) {
            coo$createCopycatType = ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(new ResourceLocation("create", "copycat"));
            coo$typeResolved = true;
        }
        if (coo$createCopycatType != null && blockEntity.getType() != coo$createCopycatType) {
            cir.setReturnValue(blockEntity);
        }
    }

    @Inject(method = "migrateStructure", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$fastSkipStructure(StructureTemplate.StructureBlockInfo info, CallbackInfoReturnable<StructureTemplate.StructureBlockInfo> cir) {
        if (!CoOConfig.copycatsFastMigrationChecks) {
            return;
        }
        Block block = info.state().getBlock();
        if (!(block instanceof MultiStateCopycatBlock) && !(block instanceof CCCopycatBlock)) {
            cir.setReturnValue(info);
        }
    }
}
