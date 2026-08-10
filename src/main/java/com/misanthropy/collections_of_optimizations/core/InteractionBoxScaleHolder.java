package com.misanthropy.collections_of_optimizations.core;

public interface InteractionBoxScaleHolder {

    int coo$boxWidthStamp();

    int coo$boxHeightStamp();

    float coo$boxWidthScale();

    float coo$boxHeightScale();

    void coo$storeBoxWidthScale(int stamp, float width);

    void coo$storeBoxHeightScale(int stamp, float height);
}
