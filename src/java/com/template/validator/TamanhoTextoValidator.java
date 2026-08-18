package com.template.validator;

public class TamanhoTextoValidator implements Validator<String> {
    private final String nomeCampo;
    private final String valor;
    private final int min;
    private final int max;

    public TamanhoTextoValidator(String nomeCampo, String valor, int min, int max) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null) {
            return false;
        }
        int tamanho = this.valor.trim().length();
        return tamanho >= min && tamanho <= max;
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve ter entre " + min + " e " + max + " caracteres.";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}