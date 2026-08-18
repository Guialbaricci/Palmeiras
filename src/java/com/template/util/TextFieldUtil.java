package com.template.util;

import javafx.scene.control.TextField;

public class TextFieldUtil {

    public static void permitirApenasNumeros(TextField... campos) {
        for (TextField campo : campos) {
            campo.textProperty().addListener((obs, antigo, novo) -> {
                if (!novo.matches("\\d*")) {
                    campo.setText(novo.replaceAll("[^\\d]", ""));
                }
            });
        }
    }

    public static void limparCampos(TextField... campos) {
        for (TextField campo : campos) {
            campo.clear();
        }
    }
}
