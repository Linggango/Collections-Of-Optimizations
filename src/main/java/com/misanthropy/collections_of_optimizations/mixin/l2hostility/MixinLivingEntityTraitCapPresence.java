package com.misanthropy.collections_of_optimizations.mixin.l2hostility;

import com.misanthropy.collections_of_optimizations.core.TraitCapPresenceHolder;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntityTraitCapPresence implements TraitCapPresenceHolder {

    @Unique
    private boolean coo$noTraitCap;

    @Override
    public boolean coo$hasNoTraitCap() {
        return this.coo$noTraitCap;
    }

    @Override
    public void coo$markNoTraitCap() {
        this.coo$noTraitCap = true;
    }
}
