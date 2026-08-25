package com.template.validator;

import com.template.util.DialogUtil;
import java.util.List;

public class JogadoresValidator {

    public static boolean validarJogador(String nome, String idade, String nacionalidade, String gols) {
        List<Validator<String>> validadores = List.of(
                new CampoObrigatorioValidator("Nome", nome),
                new TamanhoTextoValidator("Nome", nome, 2, 50),
                new CampoObrigatorioValidator("Idade", idade),
                new IdadeValidator(idade),
                new CampoObrigatorioValidator("Nacionalidade", nacionalidade),
                new TamanhoTextoValidator("Nacionalidade", nacionalidade, 2, 30),
                new CampoObrigatorioValidator("Gols", gols),
                new NumeroValidator("Gols", gols)
        );

        for (Validator<String> validator : validadores) {
            if (!validator.validar(validator.getValor())) {
                DialogUtil.mostrarErro("Validação de Campos", validator.getMensagemErro());
                return false;
            }
        }
        return true;
    }

    public static boolean validarPorID(String id) {
        List<Validator<String>> validadores = List.of(
                new CampoObrigatorioValidator("ID", id),
                new NumeroValidator("ID", id)
        );

        for (Validator<String> validator : validadores) {
            if (!validator.validar(validator.getValor())) {
                DialogUtil.mostrarErro("Seleção Inválida", validator.getMensagemErro());
                return false;
            }
        }
        return true;
    }
}