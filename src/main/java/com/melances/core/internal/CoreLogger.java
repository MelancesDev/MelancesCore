package com.melances.core.internal;

import cn.nukkit.plugin.PluginBase;
import java.util.logging.Level;

public final class CoreLogger {

    private final PluginBase plugin;

    public CoreLogger(PluginBase plugin) {
        this.plugin = plugin;
    }

    public void info(String msg) {
        plugin.getLogger().info(msg);
    }

    public void warn(String msg) {
        plugin.getLogger().warning(msg);
    }

    public void error(String msg) {
        plugin.getLogger().severe(msg);
    }

    public void error(String msg, Throwable t) {
        plugin.getLogger().log(Level.SEVERE, msg, t);
    }
}