package com.melances.core.services;

import cn.nukkit.Player;
import com.melances.core.ui.UiResult;

public interface UiService {
    boolean available();
    UiResult openTestMenu(Player p);
}
