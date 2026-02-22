package com.melances.core.module;

import com.melances.core.internal.CoreLogger;
import com.melances.core.internal.ConfigService;

import java.util.*;

public final class ModuleManager {

    private final CoreLogger log;
    private final ConfigService config;
    private final ModuleContext ctx;

    private final Map<String, FeatureModuleFactory> factories = new HashMap<>();
    private final Map<String, FeatureModule> enabled = new LinkedHashMap<>();

    public ModuleManager(CoreLogger log, ConfigService config, ModuleContext ctx) {
        this.log = log;
        this.config = config;
        this.ctx = ctx;
        discoverFactories();
    }

    private void discoverFactories() {
        ServiceLoader<FeatureModuleFactory> loader = ServiceLoader.load(FeatureModuleFactory.class);
        int found = 0;
        for (FeatureModuleFactory f : loader) {
            String id = norm(f.id());
            if (id == null) continue;
            factories.put(id, f);
            found++;
            log.info("[module] factory bulundu: " + id);
        }
        if (found == 0) log.warn("[module] Hiç feature modül bulunamadı.");
    }

    private String norm(String s) {
        if (s == null) return null;
        s = s.trim().toLowerCase(Locale.ROOT);
        return s.isBlank() ? null : s;
    }

    public void enableConfigured() {
        for (String id : factories.keySet()) {
            boolean en = config.getBoolean("modules." + id + ".enabled", false);
            if (!en) continue;
            enableOne(id);
        }
    }

    private void enableOne(String id) {
        if (enabled.containsKey(id)) return;

        FeatureModuleFactory f = factories.get(id);
        if (f == null) {
            log.warn("[module] " + id + " açılamadı: factory yok.");
            return;
        }

        FeatureModule m;
        try {
            m = f.create(ctx);
        } catch (Throwable t) {
            log.warn("[module] create fail=" + id + " err=" + t.getClass().getSimpleName());
            return;
        }

        List<String> deps = m.dependsOn() == null ? List.of() : m.dependsOn();
        for (String dep : deps) {
            dep = norm(dep);
            if (dep == null) continue;

            boolean depEnabledInConfig = config.getBoolean("modules." + dep + ".enabled", false);
            if (!depEnabledInConfig) {
                log.warn("[module] " + id + " açılmadı, çünkü bağımlılık kapalı: " + dep);
                return;
            }
            enableOne(dep);
            if (!enabled.containsKey(dep)) {
                log.warn("[module] " + id + " açılmadı, çünkü bağımlılık açılamadı: " + dep);
                return;
            }
        }

        try {
            m.onEnable();
            enabled.put(id, m);
            log.info("[module] enabled=" + id);
        } catch (Throwable t) {
            log.warn("[module] enable fail=" + id + " err=" + t.getClass().getSimpleName());
        }
    }

    public void disableAll() {
        List<String> ids = new ArrayList<>(enabled.keySet());
        Collections.reverse(ids);
        for (String id : ids) {
            FeatureModule m = enabled.remove(id);
            if (m == null) continue;
            try {
                m.onDisable();
                log.info("[module] disabled=" + id);
            } catch (Throwable t) {
                log.warn("[module] disable fail=" + id + " err=" + t.getClass().getSimpleName());
            }
        }
    }
}
