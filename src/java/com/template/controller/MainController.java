package com.template.controller;

import com.template.model.dto.JogadoresDTO;
import com.template.services.JogadoresService;
import com.template.util.DialogUtil;
import com.template.util.TextFieldUtil;
import com.template.validator.JogadoresValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

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

    private final JogadoresService jogadoresService = new JogadoresService();

    @FXML
    public void initialize() {
        configurarColunas();
        TextFieldUtil.permitirApenasNumeros(txtIdade, txtGols);
        configurarListenerSelecaoTabela();
        carregarTabela();
    }

    @FXML
    private void btnAdicionarAction(ActionEvent event) {
        if (!JogadoresValidator.validarJogador(txtNome.getText(), txtIdade.getText(), txtNacionalidade.getText(), txtGols.getText())) {
            return;
        }

        try {
            jogadoresService.cadastrar(txtNome.getText(), txtIdade.getText(), txtNacionalidade.getText(), txtGols.getText());
            DialogUtil.mostrarMensagem("Sucesso", "Jogador cadastrado com sucesso!");
            resetarTela();
        } catch (Exception e) {
            DialogUtil.mostrarErro("Erro ao Cadastrar", "Falha ao cadastrar: " + e.getMessage());
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        if (!JogadoresValidator.validarPorID(txtId.getText()) ||
                !JogadoresValidator.validarJogador(txtNome.getText(), txtIdade.getText(), txtNacionalidade.getText(), txtGols.getText())) {
            return;
        }

        try {
            jogadoresService.atualizar(txtId.getText(), txtNome.getText(), txtIdade.getText(), txtNacionalidade.getText(), txtGols.getText());
            DialogUtil.mostrarMensagem("Sucesso", "Jogador atualizado com sucesso!");
            resetarTela();
        } catch (Exception e) {
            DialogUtil.mostrarErro("Erro ao Atualizar", "Falha ao atualizar: " + e.getMessage());
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (!JogadoresValidator.validarPorID(txtId.getText())) {
            return;
        }

        if (DialogUtil.mostrarConfirmacao("Confirmação", "Deseja realmente excluir este jogador?")) {
            try {
                jogadoresService.excluir(txtId.getText());
                DialogUtil.mostrarMensagem("Sucesso", "Jogador excluído com sucesso!");
                resetarTela();
            } catch (Exception e) {
                DialogUtil.mostrarErro("Erro ao Excluir", "Falha ao excluir: " + e.getMessage());
            }
        }
    }

    @FXML
    private void btnPesquisarAction(ActionEvent event) {
        if (!JogadoresValidator.validarPorID(txtId.getText())) {
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            List<JogadoresDTO> resultado = jogadoresService.buscarPorId(id);

            tblJogadores.setItems(FXCollections.observableArrayList(resultado));

            if (resultado.isEmpty()) {
                DialogUtil.mostrarMensagem("Aviso", "Nenhum jogador encontrado com o ID fornecido.");
                limparFormulario();
            }
        } catch (NumberFormatException e) {
            DialogUtil.mostrarErro("Erro de Entrada", "Informe um ID numérico válido.");
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        resetarTela();
    }

    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        colGols.setCellValueFactory(new PropertyValueFactory<>("gols"));
        tblJogadores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configurarListenerSelecaoTabela() {
        tblJogadores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, jogador) -> {
            if (jogador != null) {
                txtId.setText(String.valueOf(jogador.getId()));
                txtNome.setText(jogador.getNome());
                txtIdade.setText(String.valueOf(jogador.getIdade()));
                txtNacionalidade.setText(jogador.getNacionalidade());
                txtGols.setText(String.valueOf(jogador.getGols()));
            }
        });
    }

    private void carregarTabela() {
        tblJogadores.setItems(FXCollections.observableArrayList(jogadoresService.listarTodos()));
    }

    private void resetarTela() {
        carregarTabela();
        limparFormulario();
    }

    private void limparFormulario() {
        TextFieldUtil.limparCampos(txtId, txtNome, txtIdade, txtNacionalidade, txtGols);
        tblJogadores.getSelectionModel().clearSelection();
    }
}