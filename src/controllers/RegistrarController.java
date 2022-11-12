package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import conts.Strings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Usuario;
import utils.MeuAlert;
import views.styles.UtilStyles;

public class RegistrarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Hyperlink linkEntrar;

    @FXML
    private PasswordField tfConfirmarSenha;

    @FXML
    private PasswordField tfSenha;

    @FXML
    private TextField tfUsuario;

    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onLinkEntrar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(Strings.entrar);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // Ocultar tela atual
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void onRegistrar(ActionEvent event) throws IOException {
        var usr = new Usuario();

        var usuario = tfUsuario.getText();
        var senha = tfSenha.getText();

        tfConfirmarSenha.setDisable(false);

        if (!usuario.isEmpty()) {
            // Salvando usuário no banco
            usr.setNomeUsuario(usuario);
            usr.setSenha(senha);

            usr.setNomeUsuario(tfUsuario.getText());
            usr.setSenha(tfSenha.getText());

            var usuariosDb = new ArrayList<String>();
            var usuarioDb = new Usuario();

            var resultadoDb = usuarioDb.listarUsuarios();

            resultadoDb.forEach((u) -> {
                usuariosDb.add(u.getNomeUsuario());
            });

            if (!usuariosDb.contains(usr.getNomeUsuario())) {
                if (usr.cadastrarUsuario(usr)) {
                    MeuAlert.mostrar("Registrado com sucesso", "Usuário registrado com sucesso", AlertType.INFORMATION);
                    reset();
                } else {
                    MeuAlert.mostrar("Erro de Registro",
                            "Ocorreu uma falha inesperada ao salvar o usuário no banco de dados!", AlertType.ERROR);
                }

            } else {
                MeuAlert.mostrar("Erro de Registro", "Este usuário existe!", AlertType.ERROR);
            }

        } else {
            MeuAlert.mostrar("Erro de Registro", "O Nome de usuário não pode estar vazio!", AlertType.ERROR);
        }
    }

    void reset() {
        UtilStyles.removeErrorBorder(tfConfirmarSenha);
        UtilStyles.removeErrorBorder(tfSenha);
        tfConfirmarSenha.setDisable(true);
        btnRegistrar.setDisable(true);
        tfConfirmarSenha.clear();
        tfUsuario.clear();
        tfSenha.clear();
    }

    @FXML
    void initialize() {
        tfConfirmarSenha.setDisable(true);
        btnRegistrar.setDisable(true);

        tfSenha.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obsSenha, String senhaOld, String senhaNew) {
                if (senhaNew.length() >= 3) {
                    tfConfirmarSenha.setDisable(false);
                    UtilStyles.setSuccessBorder(tfSenha);
                } else {
                    UtilStyles.removeSucsessBorder(tfSenha);
                    UtilStyles.setErrorBorder(tfSenha);
                    btnRegistrar.setDisable(true);
                    tfConfirmarSenha.setDisable(true);
                }

                tfConfirmarSenha.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> obsConfirmarSenha, String confirmarSenhaOld,
                            String confirmarSenhaNovo) {
                        if (confirmarSenhaNovo.equals(senhaNew)) {
                            btnRegistrar.setDisable(false);
                            UtilStyles.setSuccessBorder(tfConfirmarSenha);
                        } else {
                            btnRegistrar.setDisable(true);
                            UtilStyles.removeSucsessBorder(tfConfirmarSenha);
                            UtilStyles.setErrorBorder(tfConfirmarSenha);
                        }
                    }
                });
            }
        });
    }
}
