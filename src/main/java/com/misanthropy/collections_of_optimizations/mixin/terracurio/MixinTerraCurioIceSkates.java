package com.misanthropy.collections_of_optimizations.mixin.terracurio;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.movement.IceSkates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = IceSkates.class, remap = false)
public abstract class MixinTerraCurioIceSkates {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipUnchangedIceFlag(LivingEntity living, ItemStack stack, CallbackInfo ci) {
        if (!CoOConfig.terracurioSkipUnchangedIceFlag) {
            return;
        }

        Level level = living.level();
        if (level.isClientSide) {
            return;
        }

        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains("onPosIsIce", Tag.TAG_BYTE)) {
            return;
        }

        if (tag.getBoolean("onPosIsIce") == level.getBlockState(living.getOnPos()).is(BlockTags.ICE)) {
            ci.cancel();
        }
    }
}
