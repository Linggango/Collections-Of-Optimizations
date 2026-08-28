package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;
import java.util.Optional;

@Mixin(HierarchicalModel.class)
public abstract class MixinHierarchicalModelParts {

    @Shadow
    public abstract ModelPart root();

    @Unique
    private ModelPart coo$cachedRoot;

    @Unique
    private Map<String, Optional<ModelPart>> coo$partsByName;

    @WrapMethod(method = "getAnyDescendantWithName", require = 0)
    private Optional<ModelPart> coo$cachePartLookup(String name, Operation<Optional<ModelPart>> original) {
        if (!CoOConfig.vanillaCacheModelPartLookups || !RenderSystem.isOnRenderThread()) {
            return original.call(name);
        }

        ModelPart root = this.root();
        Map<String, Optional<ModelPart>> cache = this.coo$partsByName;
        if (cache == null || this.coo$cachedRoot != root) {
            cache = new Object2ObjectOpenHashMap<>(16);
            this.coo$partsByName = cache;
            this.coo$cachedRoot = root;
        }

        Optional<ModelPart> hit = cache.get(name);
        if (hit != null) {
            return hit;
        }

        Optional<ModelPart> value = original.call(name);
        cache.put(name, value);
        return value;
    }
}
