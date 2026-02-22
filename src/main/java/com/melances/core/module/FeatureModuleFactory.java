package com.melances.core.module;

public interface FeatureModuleFactory {
    String id();
    FeatureModule create(ModuleContext ctx);
}
