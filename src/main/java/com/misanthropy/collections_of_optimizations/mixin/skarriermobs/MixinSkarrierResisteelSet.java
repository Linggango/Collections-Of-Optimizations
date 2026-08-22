package com.misanthropy.collections_of_optimizations.mixin.skarriermobs;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mcreator.skarriermobs.init.SkarrierMobsModItems;
import net.mcreator.skarriermobs.procedures.ResisteelArmorEquippedEffectProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ResisteelArmorEquippedEffectProcedure.class, remap = false)
public abstract class MixinSkarrierResisteelSet {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipUnarmouredEntities(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.skarriermobsLeanResisteelSetTracking || event == null) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity == null || coo$wearsResisteel(entity)) {
            return;
        }
        CompoundTag data = entity instanceof SkarrierEntityDataAccessor access ? access.coo$persistentData() : null;
        if (data != null && data.getDouble("ResisteelSetCompletion") != 0.0D) {
            return;
        }
        ci.cancel();
    }

    @Unique
    private static boolean coo$wearsResisteel(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == SkarrierMobsModItems.RESISTEEL_HELMET.get()
                || entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == SkarrierMobsModItems.RESISTEEL_CHESTPLATE.get()
                || entity.getItemBySlot(EquipmentSlot.LEGS).getItem() == SkarrierMobsModItems.RESISTEEL_LEGGINGS.get()
                || entity.getItemBySlot(EquipmentSlot.FEET).getItem() == SkarrierMobsModItems.RESISTEEL_BOOTS.get();
    }
}
