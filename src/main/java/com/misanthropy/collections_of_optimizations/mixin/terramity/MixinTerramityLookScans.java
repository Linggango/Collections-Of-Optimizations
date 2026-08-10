package com.misanthropy.collections_of_optimizations.mixin.terramity;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mcreator.terramity.procedures.ConductiteScouterHelmetTickEventProcedure;
import net.mcreator.terramity.procedures.VoidGlassesWhileBaubleIsEquippedTickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {
        ConductiteScouterHelmetTickEventProcedure.class,
        VoidGlassesWhileBaubleIsEquippedTickProcedure.class
}, remap = false)
public abstract class MixinTerramityLookScans {

    @Inject(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$serverSideOnly(LevelAccessor world, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (CoOConfig.terramitySkipClientCurioScans && world != null && world.isClientSide()) {
            ci.cancel();
        }
    }
}
