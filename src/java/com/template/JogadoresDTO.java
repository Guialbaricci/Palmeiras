package com.template;

public class JogadoresDTO {
    private int id;
    private String nome;
    private int idade;
    private String nacionalidade;
    private int gols;

    //getters e setters simplificados
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public int getGols() { return gols; }
    public void setGols(int gols) { this.gols = gols; }
}
