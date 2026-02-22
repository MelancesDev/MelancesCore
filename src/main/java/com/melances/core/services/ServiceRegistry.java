package com.melances.core.services;

import com.melances.core.internal.CoreLogger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ServiceRegistry {

    private final CoreLogger log;
    private final Map<Class<?>, Object> map;

    public ServiceRegistry(CoreLogger log) {
        this.log = log;
        this.map = new ConcurrentHashMap<>();
    }

    public <T> void bind(Class<T> type, T impl) {
        map.put(type, impl);
        log.info("[service] bind " + type.getSimpleName());
    }

    public <T> T get(Class<T> type) {
        Object o = map.get(type);
        if (o == null) return null;
        return type.cast(o);
    }
}
