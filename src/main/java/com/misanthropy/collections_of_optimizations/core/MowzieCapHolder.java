package com.misanthropy.collections_of_optimizations.core;

public interface MowzieCapHolder {

    Object COO_ABSENT = new Object();

    int COO_CAP_COUNT = 4;

    Object coo$getMowzieCap(int index);

    void coo$setMowzieCap(int index, Object value);
}
