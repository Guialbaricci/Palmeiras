package com.template;

import model.dto.JogadoresDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import model.Conexao;

// Classe responsável pelas operações CRUD no banco
public class JogadoresDAO {

    private static final Logger logger = Logger.getLogger(JogadoresDAO.class.getName());

    public void cadastrarJogador(JogadoresDTO jogador) {
        String sql = "INSERT INTO jogadores (nome, idade, nacionalidade, gols) VALUES (?, ?, ?, ?)";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, jogador.getNome());
            ps.setInt(2, jogador.getIdade());
            ps.setString(3, jogador.getNacionalidade());
            ps.setInt(4, jogador.getGols());
            ps.execute();
            System.out.println("Jogador cadastrado com sucesso!");

        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar", e);
        }
    }

    public List<JogadoresDTO> selecionarJogadores() {
        String sql = "SELECT * FROM jogadores";
        List<JogadoresDTO> lista = new ArrayList<>();

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Cria um novo objeto DTO para cada linha do banco
                JogadoresDTO jogador = new JogadoresDTO();

                // Popula o objeto com os dados da linha atual
                jogador.setId(rs.getInt("id"));
                jogador.setNome(rs.getString("nome"));
                jogador.setIdade(rs.getInt("idade"));
                jogador.setNacionalidade(rs.getString("nacionalidade"));
                jogador.setGols(rs.getInt("gols"));

                // Adiciona o jogador na lista
                lista.add(jogador);
            }

        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar jogadores", e);
        }

        return lista; // Retorna a lista (estará vazia se der erro ou se não houver dados)
    }

    public void atualizarJogador(JogadoresDTO jogador) {
        String sql = "UPDATE jogadores SET nome = ?, idade = ?, nacionalidade = ?, gols = ? WHERE id = ?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, jogador.getNome());
            ps.setInt(2, jogador.getIdade());
            ps.setString(3, jogador.getNacionalidade());
            ps.setInt(4, jogador.getGols());
            ps.setInt(5, jogador.getId());

            ps.executeUpdate();
            System.out.println("Jogador atualizado com sucesso!");
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao alterar time", e);
            throw new RuntimeException("Erro ao alterar time", e);
        }
    }

    public void excluirJogador(int id) {
        String sql = "DELETE FROM jogadores WHERE id = ?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.execute();
            System.out.println("Jogador removido com sucesso!");
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir", e);
        }
    }
}