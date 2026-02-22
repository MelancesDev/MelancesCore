package com.melances.core.module;

import cn.nukkit.plugin.PluginBase;
import com.melances.core.commands.CommandRegistry;
import com.melances.core.internal.Chat;
import com.melances.core.internal.ConfigService;
import com.melances.core.internal.CoreLogger;
import com.melances.core.internal.DataSource;
import com.melances.core.internal.DebugService;
import com.melances.core.internal.LangService;
import com.melances.core.services.ServiceRegistry;

public final class ModuleContext {

    private final PluginBase plugin;
    private final CoreLogger log;
    private final ConfigService config;
    private final DebugService debug;
    private final LangService lang;
    private final Chat chat;
    private final DataSource db;
    private final ServiceRegistry services;
    private final CommandRegistry commands;

    public ModuleContext(
            PluginBase plugin,
            CoreLogger log,
            ConfigService config,
            DebugService debug,
            LangService lang,
            Chat chat,
            DataSource db,
            ServiceRegistry services,
            CommandRegistry commands
    ) {
        this.plugin = plugin;
        this.log = log;
        this.config = config;
        this.debug = debug;
        this.lang = lang;
        this.chat = chat;
        this.db = db;
        this.services = services;
        this.commands = commands;
    }

    public PluginBase plugin() { return plugin; }
    public CoreLogger log() { return log; }
    public ConfigService config() { return config; }
    public DebugService debug() { return debug; }
    public LangService lang() { return lang; }
    public Chat chat() { return chat; }
    public DataSource db() { return db; }
    public ServiceRegistry services() { return services; }
    public CommandRegistry commands() { return commands; }
}
