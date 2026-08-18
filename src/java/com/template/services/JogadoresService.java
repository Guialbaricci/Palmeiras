package com.template.services;

import com.template.model.dao.JogadoresDAO;
import com.template.model.dto.JogadoresDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JogadoresService{

    private final JogadoresDAO jogadoresDAO;

    public JogadoresService() {
        this.jogadoresDAO = new JogadoresDAO();
    }

    public List<JogadoresDTO> listarTodos() {
        return jogadoresDAO.selecionarJogadores();
    }

    public List<JogadoresDTO> buscarPorId(int id) {
        ArrayList<JogadoresDTO> todos = jogadoresDAO.selecionarJogadores();
        return todos.stream()
                .filter(j -> j.getId() == id)
                .collect(Collectors.toList());
    }

    public void cadastrar(String nome, String idade, String nacionalidade, String gols) {
        JogadoresDTO jogador = JogadorDTO(null, nome, idade, nacionalidade, gols);
        jogadoresDAO.cadastrarJogador(jogador);
    }

    public void atualizar(String id, String nome, String idade, String nacionalidade, String gols) {
        JogadoresDTO jogador = JogadorDTO(id, nome, idade, nacionalidade, gols);
        jogadoresDAO.atualizarJogador(jogador);
    }

    public void excluir(String id) {
        int idExcluir = Integer.parseInt(id.trim());
        jogadoresDAO.excluirJogador(idExcluir);
    }

    private JogadoresDTO JogadorDTO(String id, String nome, String idade, String nacionalidade, String gols) {
        JogadoresDTO jogador = new JogadoresDTO();
        if (id != null && !id.isBlank()) {
            jogador.setId(Integer.parseInt(id.trim()));
        }
        jogador.setNome(nome.trim());
        jogador.setIdade(Integer.parseInt(idade.trim()));
        jogador.setNacionalidade(nacionalidade.trim());
        jogador.setGols(Integer.parseInt(gols.trim()));
        return jogador;
    }
}