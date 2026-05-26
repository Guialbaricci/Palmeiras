package com.template;

public class ConexaoBD {
    static String conexao = "jdbc:postgresql://localhost:5432/BancoJogadores";
    static String usuario = "postgres";
    static String senha = "postgres";

    public Connection conectaBD() {
        try {
            return DriverManager.getConnection(conexao, usuario, senha);
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
