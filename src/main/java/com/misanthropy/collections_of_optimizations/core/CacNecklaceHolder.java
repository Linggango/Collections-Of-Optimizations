package com.misanthropy.collections_of_optimizations.core;

import java.util.Optional;

public interface CacNecklaceHolder {

    Optional<?> coo$cacNecklace();

    long coo$cacNecklaceStamp();

    void coo$storeCacNecklace(Optional<?> necklace, long stamp);
}
