package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class FastVisitedList extends ArrayList<BlockPos> {

    private final Set<BlockPos> seen = new HashSet<>();

    @Override
    public boolean add(BlockPos pos) {
        this.seen.add(pos);
        return super.add(pos);
    }

    @Override
    public boolean contains(Object o) {
        return this.seen.contains(o);
    }

    @Override
    public void clear() {
        this.seen.clear();
        super.clear();
    }
}
