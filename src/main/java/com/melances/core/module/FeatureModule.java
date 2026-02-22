package com.melances.core.module;

import java.util.List;

public interface FeatureModule {
    String id();
    List<String> dependsOn();
    void onEnable();
    void onDisable();
}
