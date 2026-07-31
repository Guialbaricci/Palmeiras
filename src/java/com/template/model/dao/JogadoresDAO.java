package com.template.model.dao;

import com.template.model.dto.JogadoresDTO;
import com.template.model.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JogadoresDAO {

    public void cadastrarJogador(JogadoresDTO jogador) {
        String sql = "INSERT INTO jogadores (nome, idade, nacionalidade, gols) VALUES (?, ?, ?, ?)";

        try (Connection c = new ConexaoBD().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, jogador.getNome());
            ps.setInt(2, jogador.getIdade());
            ps.setString(3, jogador.getNacionalidade());
            ps.setInt(4, jogador.getGols());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar jogador", e);
        }
    }

    public ArrayList<JogadoresDTO> selecionarJogadores() {
        String sql = "SELECT * FROM jogadores";

        ArrayList<JogadoresDTO> listaJogadores = new ArrayList<>();

        try (Connection c = new ConexaoBD().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                JogadoresDTO jogador = new JogadoresDTO();

                jogador.setId(rs.getInt("id"));
                jogador.setNome(rs.getString("nome"));
                jogador.setIdade(rs.getInt("idade"));
                jogador.setNacionalidade(rs.getString("nacionalidade"));
                jogador.setGols(rs.getInt("gols"));

                listaJogadores.add(jogador);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao listar jogadores", e);
        }
        return listaJogadores;
    }

    public void atualizarJogador(JogadoresDTO jogador) {
        String sql =
                "UPDATE jogadores "+"SET nome = ?, idade = ?, nacionalidade = ?, gols = ? "+"WHERE id = ?";

        try (Connection c = new ConexaoBD().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, jogador.getNome());
            ps.setInt(2, jogador.getIdade());
            ps.setString(3, jogador.getNacionalidade());
            ps.setInt(4, jogador.getGols());
            ps.setInt(5, jogador.getId());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar jogador", e);
        }
    }

    public void excluirJogador(int id) {
        String sql = "DELETE FROM jogadores WHERE id = ?";

        try (Connection c = new ConexaoBD().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir jogador", e);
        }
    }
}