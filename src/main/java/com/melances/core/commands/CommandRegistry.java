package com.melances.core.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import com.melances.core.internal.CoreLogger;

import java.util.List;
import java.util.function.BiConsumer;

public final class CommandRegistry {

    private final PluginBase plugin;
    private final CoreLogger log;

    public CommandRegistry(PluginBase plugin, CoreLogger log) {
        this.plugin = plugin;
        this.log = log;
    }

    public void register(String name, String description, String usage, BiConsumer<CommandSender, String[]> handler, List<String> aliases) {
        Command cmd = new Command(
                name,
                description,
                usage,
                aliases.toArray(new String[0])
        ) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                handler.accept(sender, args);
                return true;
            }
        };

        plugin.getServer().getCommandMap().register(plugin.getName(), cmd);
        log.info("[cmd] /" + name + " kayıt edildi");
    }
}