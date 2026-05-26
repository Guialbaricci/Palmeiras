package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

public class MainController {

    @FXML private Button btnSalvar;
    @FXML private Button btnExcluir;
    @FXML private Button btnAdicionar;
    @FXML private Button btnAlterar;

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

    private ObservableList<JogadoresDTO> listaJogadores = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        colGols.setCellValueFactory(new PropertyValueFactory<>("gols"));

        carregarJogadores();

        System.out.println("FXML loaded successfully!");
    }

    @FXML
    private void btnAdicionarAction(ActionEvent event) {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            int idade = Integer.parseInt(txtIdade.getText());
            String nacionalidade = txtNacionalidade.getText();
            int gols = Integer.parseInt(txtGols.getText());

            JogadoresDTO jogador = new JogadoresDTO();

            jogador.setId(id);
            jogador.setNome(nome);
            jogador.setIdade(idade);
            jogador.setNacionalidade(nacionalidade);
            jogador.setGols(gols);

            JogadoresDAO dao = new JogadoresDAO();
            dao.cadastrarJogador(jogador);

            carregarJogadores();
            limparCampos();
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
            JogadoresDTO jogador = new JogadoresDTO();

            jogador.setId(Integer.parseInt(txtId.getText()));
            jogador.setNome(txtNome.getText());
            jogador.setIdade(Integer.parseInt(txtIdade.getText()));
            jogador.setNacionalidade(txtNacionalidade.getText());
            jogador.setGols(Integer.parseInt(txtGols.getText()));

            JogadoresDAO dao = new JogadoresDAO();
            dao.alterarJogador(jogador);

            carregarJogadores();
            limparCampos();
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
            int id = Integer.parseInt(txtId.getText());

            JogadoresDAO dao = new JogadoresDAO();
            dao.excluirJogador(id);

            carregarJogadores();
            limparCampos();
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtIdade.clear();
        txtNacionalidade.clear();
        txtGols.clear();
    }

    private void carregarJogadores() {

    }
}