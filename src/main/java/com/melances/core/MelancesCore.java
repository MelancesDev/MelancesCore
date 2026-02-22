package com.melances.core;

import cn.nukkit.plugin.PluginBase;
import com.melances.core.internal.Core;
import com.melances.core.internal.CoreLogger;

public final class MelancesCore extends PluginBase {

    private Core core;

    @Override
    public void onLoad() {
        try {
            saveDefaultConfig();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onEnable() {
        CoreLogger log = new CoreLogger(this);
        this.core = new Core(this, log);
        this.core.enable();
    }

    @Override
    public void onDisable() {
        if (core != null) core.disable();
    }
}
