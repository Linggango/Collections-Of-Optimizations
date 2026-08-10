package com.misanthropy.collections_of_optimizations.mixin.pehkui;

import com.misanthropy.collections_of_optimizations.core.InteractionBoxScaleHolder;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Entity.class)
public abstract class MixinEntityBoxScaleCache implements InteractionBoxScaleHolder {

    @Unique
    private int coo$boxWidthStamp = Integer.MIN_VALUE;

    @Unique
    private int coo$boxHeightStamp = Integer.MIN_VALUE;

    @Unique
    private float coo$boxWidth = 1.0F;

    @Unique
    private float coo$boxHeight = 1.0F;

    @Override
    public int coo$boxWidthStamp() {
        return this.coo$boxWidthStamp;
    }

    @Override
    public int coo$boxHeightStamp() {
        return this.coo$boxHeightStamp;
    }

    @Override
    public float coo$boxWidthScale() {
        return this.coo$boxWidth;
    }

    @Override
    public float coo$boxHeightScale() {
        return this.coo$boxHeight;
    }

    @Override
    public void coo$storeBoxWidthScale(int stamp, float width) {
        this.coo$boxWidth = width;
        this.coo$boxWidthStamp = stamp;
    }

    @Override
    public void coo$storeBoxHeightScale(int stamp, float height) {
        this.coo$boxHeight = height;
        this.coo$boxHeightStamp = stamp;
    }
}
