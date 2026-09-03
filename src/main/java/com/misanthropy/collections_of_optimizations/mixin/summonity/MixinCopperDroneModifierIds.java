package com.misanthropy.collections_of_optimizations.mixin.summonity;

import com.fevzi.summonity.entity.SummonedCopperDroneEntity;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(value = SummonedCopperDroneEntity.class, remap = false)
public abstract class MixinCopperDroneModifierIds {

    @Unique
    private UUID coo$modifierIdOwner;

    @Unique
    private UUID coo$armorModifierId;

    @Unique
    private UUID coo$speedModifierId;

    @Inject(method = "modifierId", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$answerFromMemo(String bonus, CallbackInfoReturnable<UUID> cir) {
        if (!CoOConfig.summonityMemoDroneModifierIds || this.coo$modifierIdOwner != ((Entity) (Object) this).getUUID()) {
            return;
        }
        if ("armor".equals(bonus)) {
            if (this.coo$armorModifierId != null) {
                cir.setReturnValue(this.coo$armorModifierId);
            }
        } else if ("speed".equals(bonus) && this.coo$speedModifierId != null) {
            cir.setReturnValue(this.coo$speedModifierId);
        }
    }

    @Inject(method = "modifierId", at = @At("RETURN"), require = 0)
    private void coo$rememberId(String bonus, CallbackInfoReturnable<UUID> cir) {
        if (!CoOConfig.summonityMemoDroneModifierIds) {
            return;
        }
        UUID owner = ((Entity) (Object) this).getUUID();
        if (this.coo$modifierIdOwner != owner) {
            this.coo$modifierIdOwner = owner;
            this.coo$armorModifierId = null;
            this.coo$speedModifierId = null;
        }
        if ("armor".equals(bonus)) {
            this.coo$armorModifierId = cir.getReturnValue();
        } else if ("speed".equals(bonus)) {
            this.coo$speedModifierId = cir.getReturnValue();
        }
    }
}
