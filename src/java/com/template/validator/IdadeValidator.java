package com.template.validator;

public class IdadeValidator implements Validator<String> {
    private final String valor;
    private final int idadeMinima;
    private final int idadeMaxima;

    public IdadeValidator(String valor) {
        this(valor, 14, 60);
    }

    public IdadeValidator(String valor, int idadeMinima, int idadeMaxima) {
        this.valor = valor;
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return false;
        }
        try {
            int idade = Integer.parseInt(this.valor.trim());
            return idade >= idadeMinima && idade <= idadeMaxima;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "A idade deve ser um número inteiro válido entre " + idadeMinima + " e " + idadeMaxima + " anos.";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}