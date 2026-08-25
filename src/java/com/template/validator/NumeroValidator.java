package com.template.validator;

public class NumeroValidator implements Validator<String> {
    private final String nomeCampo;
    private final String valor;

    public NumeroValidator(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return false;
        }
        try {
            int num = Integer.parseInt(this.valor.trim());
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve conter um número inteiro válido e não negativo.";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}