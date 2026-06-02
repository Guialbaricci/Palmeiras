package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/BancoJogadores";

    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public Connection conectaBD() {

        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco: "
                    + e.getMessage());
        }
    }
}