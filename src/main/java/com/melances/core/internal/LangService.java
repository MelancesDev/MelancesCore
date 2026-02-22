package com.melances.core.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class LangService {

    private final Supplier<String> prefix;
    private final Map<String, String> map;

    public LangService(Supplier<String> prefix) {
        this.prefix = prefix;
        this.map = new HashMap<>();
        seed();
    }

    public String prefix() {
        return prefix.get();
    }

    public String msg(String key) {
        return map.getOrDefault(key, key);
    }

    public String msg(String key, Map<String, String> vars) {
        String s = msg(key);
        for (Map.Entry<String, String> e : vars.entrySet()) {
            s = s.replace("{" + e.getKey() + "}", e.getValue());
        }
        return s;
    }

    private void seed() {
        map.put("core.playerOnly", "Bu komut sadece oyuncu içindir.");
        map.put("core.ok", "Tamam.");
        map.put("core.reloaded", "Yeniden yüklendi.");
        map.put("ui.unavailable", "UI açılamadı.");
        map.put("ui.formsMissing", "Forms API bulunamadı.");
    }
}
