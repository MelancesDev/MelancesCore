package com.melances.core.internal;

import cn.nukkit.command.CommandSender;

public final class Chat {

    private final LangService lang;
    private final DebugService debug;

    public Chat(LangService lang, DebugService debug) {
        this.lang = lang;
        this.debug = debug;
    }

    public void info(CommandSender s, String msg) {
        s.sendMessage(lang.prefix() + msg);
    }

    public void error(CommandSender s, String where, Throwable t) {
        String reason = Errors.shortReason(t);
        String detail = Errors.sanitize(t == null ? "" : t.getMessage(), debug.maxChatErrorLen());
        String out;

        if (!debug.enabled()) {
            out = "§cHATA§r " + Errors.sanitize(where, 60) + " - " + Errors.sanitize(reason, 60);
        } else {
            String clazz = (t == null) ? "" : t.getClass().getSimpleName();
            String right = reason;
            if (detail != null && !detail.isBlank()) right = right + " (" + detail + ")";
            out = "§cHATA§r Hata Veren: " + Errors.sanitize(where, 80) + " - Hata sebebi: " + Errors.sanitize(clazz, 60) + " " + Errors.sanitize(right, debug.maxChatErrorLen());
        }

        s.sendMessage(lang.prefix() + out);
    }
}
