package com.melances.core.ui;

import cn.nukkit.Player;
import com.melances.core.services.UiService;

public final class NoFormsUiService implements UiService {

    private final UiDiagnostics diag;

    public NoFormsUiService(UiDiagnostics diag) {
        this.diag = diag;
    }

    @Override
    public boolean available() {
        return false;
    }

    @Override
    public UiResult openTestMenu(Player p) {
        String where = "UiService#openTestMenu";
        String miss = String.join(", ", diag.missing());
        String human = miss.isBlank() ? "Forms API bulunamadı." : "Forms API bulunamadı. Eksik: " + miss;
        return UiResult.fail(where, "FORMS_MISSING", human, null);
    }
}
