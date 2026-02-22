package com.melances.core.ui;

import com.melances.core.internal.CoreLogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UiDiagnostics {

    private final CoreLogger log;
    private final List<String> missing;

    public UiDiagnostics(CoreLogger log) {
        this.log = log;
        this.missing = new ArrayList<>();
    }

    public void detect() {
        missing.clear();
        require("cn.nukkit.form.window.FormWindowSimple");
        require("cn.nukkit.form.window.FormWindowModal");
        require("cn.nukkit.form.window.FormWindowCustom");
        require("cn.nukkit.form.response.FormResponseSimple");
        require("cn.nukkit.form.response.FormResponseModal");
        require("cn.nukkit.form.response.FormResponseCustom");
        require("cn.nukkit.event.player.PlayerFormRespondedEvent");
    }

    private void require(String clazz) {
        try {
            Class.forName(clazz);
        } catch (Throwable t) {
            missing.add(clazz);
        }
    }

    public boolean formsAvailable() {
        return missing.isEmpty();
    }

    public List<String> missing() {
        return Collections.unmodifiableList(missing);
    }

    public void logSummary(boolean debug) {
        if (formsAvailable()) {
            log.info("[ui] Forms=var");
            return;
        }
        if (!debug) {
            log.warn("[ui] Forms=yok");
            return;
        }
        log.warn("[ui] Forms=yok eksikler=" + String.join(", ", missing));
    }
}
