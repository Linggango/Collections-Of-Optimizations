package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.misanthropy.collections_of_optimizations.core.MowzieCapHolder;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntityMowzieCaps implements MowzieCapHolder {

    @Unique
    private Object[] coo$mowzieCaps;

    @Override
    public Object coo$getMowzieCap(int index) {
        Object[] slots = this.coo$mowzieCaps;
        return slots == null ? null : slots[index];
    }

    @Override
    public void coo$setMowzieCap(int index, Object value) {
        Object[] slots = this.coo$mowzieCaps;
        if (slots == null) {
            slots = new Object[MowzieCapHolder.COO_CAP_COUNT];
            this.coo$mowzieCaps = slots;
        }
        slots[index] = value;
    }
}
