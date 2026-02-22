package com.melances.core.internal;

public final class DebugService {

    private final ConfigService config;

    public DebugService(ConfigService config) {
        this.config = config;
    }

    public boolean enabled() {
        return config.getBoolean("debug.enabled", true);
    }

    public boolean uiDiagnostics() {
        return config.getBoolean("debug.uiDiagnostics", true);
    }

    public boolean stacktraceInChat() {
        return config.getBoolean("debug.stacktraceInChat", false);
    }

    public int maxChatErrorLen() {
        return Math.max(60, config.getInt("debug.maxChatErrorLen", 180));
    }
}
