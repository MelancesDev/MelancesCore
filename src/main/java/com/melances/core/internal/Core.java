package com.melances.core.internal;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import com.melances.core.commands.CommandRegistry;
import com.melances.core.module.ModuleContext;
import com.melances.core.module.ModuleManager;
import com.melances.core.services.ServiceRegistry;
import com.melances.core.services.UiService;
import com.melances.core.ui.FormsUiService;
import com.melances.core.ui.NoFormsUiService;
import com.melances.core.ui.UiDiagnostics;
import com.melances.core.ui.UiResult;

import java.io.File;

public final class Core {

    private final PluginBase plugin;
    private final CoreLogger log;

    private final ConfigService config;
    private final DebugService debug;
    private final LangService lang;
    private final Chat chat;
    private final DataSource db;
    private final ServiceRegistry services;
    private final CommandRegistry commands;
    private final UiDiagnostics uiDiag;

    private ModuleManager modules;
    private UiService ui;

    public Core(PluginBase plugin, CoreLogger log) {
        this.plugin = plugin;
        this.log = log;

        this.config = new ConfigService(plugin);
        this.config.load();

        this.debug = new DebugService(config);
        this.lang = new LangService(() -> config.getString("chat.prefix", "§8[§c!§8] §6Sistem §8> §r"));
        this.chat = new Chat(lang, debug);

        this.db = new DataSource(log);
        this.services = new ServiceRegistry(log);
        this.commands = new CommandRegistry(plugin, log, lang);

        this.uiDiag = new UiDiagnostics(log);
    }

    public void enable() {
        log.info("[core] MelancesCore aktif ediliyor");
        if (debug.uiDiagnostics()) uiDiag.detect();
        uiDiag.logSummary(debug.enabled());

        bindServices();
        openDb();
        registerCoreCommands();

        ModuleContext ctx = new ModuleContext(plugin, log, config, debug, lang, chat, db, services, commands);
        this.modules = new ModuleManager(log, config, ctx);
        this.modules.enableConfigured();

        log.info("[core] Core aktif.");
    }

    public void disable() {
        if (modules != null) modules.disableAll();
        db.close();
    }

    private void bindServices() {
        services.bind(CoreLogger.class, log);
        services.bind(ConfigService.class, config);
        services.bind(DebugService.class, debug);
        services.bind(LangService.class, lang);
        services.bind(Chat.class, chat);
        services.bind(DataSource.class, db);
        services.bind(ServiceRegistry.class, services);
        services.bind(CommandRegistry.class, commands);

        if (uiDiag.formsAvailable()) {
            ui = new FormsUiService();
        } else {
            ui = new NoFormsUiService(uiDiag);
        }
        services.bind(UiService.class, ui);
    }

    private void openDb() {
        try {
            String fileName = config.getString("db.file", "data.sqlite");
            File dbFile = new File(plugin.getDataFolder(), fileName);
            db.open(dbFile);
        } catch (Exception e) {
            log.error("[core] Veritabanı açılamadı, plugin kapatılıyor.", e);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private void registerCoreCommands() {
        commands.register(
                "rn",
                "Core komutları",
                "/rn | /rn reload",
                "",
                this::onRn,
                "melances"
        );

        commands.register(
                "testmenu",
                "UI test menüsü",
                "/testmenu",
                "",
                this::onTestMenu
        );
    }

    private void onRn(CommandSender sender, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            config.reload();
            chat.info(sender, "§a" + lang.msg("core.reloaded"));
            if (debug.uiDiagnostics()) uiDiag.detect();
            uiDiag.logSummary(debug.enabled());
            return;
        }

        chat.info(sender, "§7Core çalışıyor. Debug=" + debug.enabled() + " Forms=" + uiDiag.formsAvailable());
    }

    private void onTestMenu(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            chat.info(sender, "§c" + lang.msg("core.playerOnly"));
            return;
        }

        UiResult r = ui.openTestMenu(p);
        if (!r.success) {
            Throwable t = r.error;
            String where = r.where == null ? "UiService#openTestMenu" : r.where;
            if (t == null) {
                chat.error(sender, where, new IllegalStateException(r.reasonHuman));
            } else {
                chat.error(sender, where, t);
            }
        }
    }
}
