package com.melances.core.ui;

import cn.nukkit.Player;
import cn.nukkit.form.element.Button;
import cn.nukkit.form.element.Toggle;
import cn.nukkit.form.window.CustomForm;
import cn.nukkit.form.window.ModalForm;
import cn.nukkit.form.window.SimpleForm;

public final class FormsUiService {

    public void openTestMenu(Player player) {
        SimpleForm form = new SimpleForm("Test Menü", "Seçim yap");

        form.addButton(new Button("Modal Test"));
        form.addButton(new Button("Custom Test"));
        form.addButton(new Button("Kapat"));

        form.onSubmit((p, response) -> {
            int id = response.getClickedButtonId();
            if (id == 0) {
                openModal(p);
            } else if (id == 1) {
                openCustom(p);
            }
        });

        form.send(player);
    }

    private void openModal(Player player) {
        ModalForm modal = new ModalForm(
                "Modal Test",
                "Devam etmek istiyor musun?",
                "Evet",
                "Hayır"
        );

        modal.onSubmit((p, response) -> {
        });

        modal.send(player);
    }

    private void openCustom(Player player) {
        CustomForm form = new CustomForm("Custom Test");
        form.addElement(new Toggle("Toggle", false));
        form.onSubmit((p, response) -> {
        });
        form.send(player);
    }
}