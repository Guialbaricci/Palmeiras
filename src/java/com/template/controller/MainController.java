package com.template.controller;

import com.template.model.dao.JogadoresDAO;
import com.template.model.dto.JogadoresDTO;
import com.template.util.DialogUtil;
import com.template.validator.JogadoresValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class MainController {

    @FXML private Button btnAdicionar;
    @FXML private Button btnPesquisar;
    @FXML private Button btnExcluir;
    @FXML private Button btnAtualizar;
    @FXML private Button btnLimpar;

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtNacionalidade;
    @FXML private TextField txtGols;

    @FXML private TableView<JogadoresDTO> tblJogadores;

    @FXML private TableColumn<JogadoresDTO, Integer> colId;
    @FXML private TableColumn<JogadoresDTO, String> colNome;
    @FXML private TableColumn<JogadoresDTO, Integer> colIdade;
    @FXML private TableColumn<JogadoresDTO, String> colNacionalidade;
    @FXML private TableColumn<JogadoresDTO, Integer> colGols;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        colGols.setCellValueFactory(new PropertyValueFactory<>("gols"));

        tblJogadores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        selecionarJogadores();

        // Restringe entrada apenas para números
        txtIdade.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*")) {
                txtIdade.setText(novo.replaceAll("[^\\d]", ""));
            }
        });

        txtGols.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*")) {
                txtGols.setText(novo.replaceAll("[^\\d]", ""));
            }
        });

        // Preenche os campos ao selecionar uma linha na tabela
        tblJogadores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, jogador) -> {
            if (jogador != null) {
                txtId.setText(String.valueOf(jogador.getId()));
                txtNome.setText(jogador.getNome());
                txtIdade.setText(String.valueOf(jogador.getIdade()));
                txtNacionalidade.setText(jogador.getNacionalidade());
                txtGols.setText(String.valueOf(jogador.getGols()));
            }
        });
    }

    @FXML
    private void btnAdicionarAction(ActionEvent event) {
        if (!JogadoresValidator.validarJogador(txtNome.getText().trim(), txtIdade.getText().trim(), txtNacionalidade.getText().trim(), txtGols.getText().trim())) {
            return;
        }

        try {
            JogadoresDTO jogador = new JogadoresDTO();
            jogador.setNome(txtNome.getText().trim());
            jogador.setIdade(Integer.parseInt(txtIdade.getText().trim()));
            jogador.setNacionalidade(txtNacionalidade.getText().trim());
            jogador.setGols(Integer.parseInt(txtGols.getText().trim()));

            JogadoresDAO novoJogadorDAO = new JogadoresDAO();
            novoJogadorDAO.cadastrarJogador(jogador);

            DialogUtil.mostrarMensagem("Sucesso", "Jogador cadastrado com sucesso!");
            selecionarJogadores();
            limparCampos();
        } catch (Exception e) {
            DialogUtil.mostrarErro("Erro ao Cadastrar", "Não foi possível cadastrar o jogador: " + e.getMessage());
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        if (!JogadoresValidator.validarPorID(txtId.getText().trim()) ||
                !JogadoresValidator.validarJogador(txtNome.getText().trim(), txtIdade.getText().trim(), txtNacionalidade.getText().trim(), txtGols.getText().trim())) {
            return;
        }

        try {
            JogadoresDTO jogador = new JogadoresDTO();
            jogador.setId(Integer.parseInt(txtId.getText().trim()));
            jogador.setNome(txtNome.getText().trim());
            jogador.setIdade(Integer.parseInt(txtIdade.getText().trim()));
            jogador.setNacionalidade(txtNacionalidade.getText().trim());
            jogador.setGols(Integer.parseInt(txtGols.getText().trim()));

            JogadoresDAO jogadorAtualizadoDAO = new JogadoresDAO();
            jogadorAtualizadoDAO.atualizarJogador(jogador);

            DialogUtil.mostrarMensagem("Sucesso", "Jogador atualizado com sucesso!");
            selecionarJogadores();
            limparCampos();
        } catch (Exception e) {
            DialogUtil.mostrarErro("Erro ao Atualizar", "Não foi possível atualizar o jogador: " + e.getMessage());
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (!JogadoresValidator.validarPorID(txtId.getText().trim())) {
            return;
        }

        boolean confirmado = DialogUtil.mostrarConfirmacao("Confirmação", "Deseja realmente excluir este jogador?");

        if (confirmado) {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                JogadoresDAO jogadorExcluidoDAO = new JogadoresDAO();
                jogadorExcluidoDAO.excluirJogador(id);

                DialogUtil.mostrarMensagem("Sucesso", "Jogador excluído com sucesso!");
                selecionarJogadores();
                limparCampos();
            } catch (Exception e) {
                DialogUtil.mostrarErro("Erro ao Excluir", "Não foi possível excluir o jogador: " + e.getMessage());
            }
        }
    }

    @FXML
    private void btnPesquisarAction(ActionEvent event) {
        if (!JogadoresValidator.validarPorID(txtId.getText().trim())) {
            return;
        }

        try {
            int idPesquisa = Integer.parseInt(txtId.getText().trim());
            JogadoresDAO jogadorPesquisadoDAO = new JogadoresDAO();
            ArrayList<JogadoresDTO> lista = jogadorPesquisadoDAO.selecionarJogadores();

            ObservableList<JogadoresDTO> resultado = FXCollections.observableArrayList();

            for (JogadoresDTO jogador : lista) {
                if (jogador.getId() == idPesquisa) {
                    resultado.add(jogador);
                }
            }

            tblJogadores.setItems(resultado);

            if (resultado.isEmpty()) {
                DialogUtil.mostrarMensagem("Aviso", "Nenhum jogador encontrado com o ID fornecido.");
                limparCampos();
            }
        } catch (NumberFormatException e) {
            DialogUtil.mostrarErro("Erro de Entrada", "Informe um número de ID válido.");
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        selecionarJogadores();
        limparCampos();
    }

    private void selecionarJogadores() {
        JogadoresDAO dao = new JogadoresDAO();
        ArrayList<JogadoresDTO> listaJogadores = dao.selecionarJogadores();
        tblJogadores.setItems(FXCollections.observableArrayList(listaJogadores));
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtIdade.clear();
        txtNacionalidade.clear();
        txtGols.clear();
        tblJogadores.getSelectionModel().clearSelection();
    }
}