package com.template.validator;

import com.template.util.DialogUtil;

public class JogadoresValidator {
    public static boolean validarJogador(String nome, String idade, String nacionalidade, String gols) {
        if (nome.isEmpty() || idade.isEmpty() || nacionalidade.isEmpty() || gols.isEmpty()) {
            DialogUtil.mostrarErro("Campos Obrigatórios", "Preencha todos os campos antes de prosseguir.");
            return false;
        }
        return true;
    }

    public static boolean validarPorID(String id) {
        if (id.isEmpty()) {
            DialogUtil.mostrarErro("Seleção Inválida", "Selecione um jogador na tabela para excluir.");
            return false;
        }
        return true;
    }

    public static boolean estaVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
