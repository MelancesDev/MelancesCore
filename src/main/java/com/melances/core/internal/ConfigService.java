package com.melances.core.internal;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public final class ConfigService {

    private final PluginBase plugin;
    private Config raw;

    public ConfigService(PluginBase plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.saveDefaultConfig();
        raw = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        raw = plugin.getConfig();
    }

    public Config raw() {
        return raw;
    }

    public boolean getBoolean(String path, boolean def) {
        return raw.getBoolean(path, def);
    }

    public int getInt(String path, int def) {
        return raw.getInt(path, def);
    }

    public long getLong(String path, long def) {
        Object o = raw.get(path);
        if (o instanceof Number n) return n.longValue();
        try {
            return Long.parseLong(String.valueOf(o));
        } catch (Exception ignored) {
            return def;
        }
    }

    public String getString(String path, String def) {
        return raw.getString(path, def);
    }
}
