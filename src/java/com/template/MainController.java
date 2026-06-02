package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class MainController {

    @FXML private Button btnAdicionar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;

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

    private ObservableList<JogadoresDTO> listaJogadores =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        colGols.setCellValueFactory(new PropertyValueFactory<>("gols"));

        selecionarJogadores();


        tblJogadores.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, jogador) -> {
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
    private void carregarCampos() {
        JogadoresDTO jogador = tblJogadores
                .getSelectionModel()
                .getSelectedItem();

        if (jogador != null) {

            txtId.setText(String.valueOf(jogador.getId()));
            txtNome.setText(jogador.getNome());
            txtIdade.setText(String.valueOf(jogador.getIdade()));
            txtNacionalidade.setText(jogador.getNacionalidade());
            txtGols.setText(String.valueOf(jogador.getGols()));
        }
    }

    @FXML
    private void btnAdicionarAction(ActionEvent event) {
            JogadoresDTO jogador = new JogadoresDTO();

            jogador.setNome(txtNome.getText());
            jogador.setIdade(Integer.parseInt(txtIdade.getText()));
            jogador.setNacionalidade(txtNacionalidade.getText());
            jogador.setGols(Integer.parseInt(txtGols.getText()));

            JogadoresDAO dao = new JogadoresDAO();
            dao.cadastrarJogador(jogador);

            selecionarJogadores();
            limparCampos();
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
            JogadoresDTO jogador = new JogadoresDTO();
            jogador.setId(Integer.parseInt(txtId.getText()));
            jogador.setNome(txtNome.getText());
            jogador.setIdade(Integer.parseInt(txtIdade.getText()));
            jogador.setNacionalidade(txtNacionalidade.getText());
            jogador.setGols(Integer.parseInt(txtGols.getText()));

            JogadoresDAO dao = new JogadoresDAO();

            dao.atualizarJogador(jogador);

            selecionarJogadores();
            limparCampos();
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
            int id = Integer.parseInt(txtId.getText());
            JogadoresDAO dao = new JogadoresDAO();
            dao.excluirJogador(id);
            selecionarJogadores();
            limparCampos();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        JogadoresDTO jogador = new JogadoresDTO();

        jogador.setNome(txtNome.getText());
        jogador.setIdade(Integer.parseInt(txtIdade.getText()));
        jogador.setNacionalidade(txtNacionalidade.getText());
        jogador.setGols(Integer.parseInt(txtGols.getText()));

        JogadoresDAO dao = new JogadoresDAO();
        dao.cadastrarJogador(jogador);

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
    }
}