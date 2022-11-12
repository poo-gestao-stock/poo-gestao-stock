package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import conts.Strings;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import models.Usuario;
import utils.MeuAlert;
import views.styles.UtilStyles;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkCadastrar;

    @FXML
    private PasswordField tfSenha;

    @FXML
    private TextField tfUsuario;

    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onLinkCadastrar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/RegistrarView.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle(Strings.registrar);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // Ocultar tela atual
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void onLogin(ActionEvent event) throws IOException {
        var usuario = new Usuario();
        usuario.setNomeUsuario(tfUsuario.getText());
        usuario.setSenha(tfSenha.getText());

        var usuariosDb = new ArrayList<String>();
        var usuarioDb = new Usuario();

        var resultadoDb = usuarioDb.listarUsuarios();

        resultadoDb.forEach((u) -> {
            usuariosDb.add(u.getNomeUsuario() + ":" + u.getSenha());
        });

        if (usuario.getSenha().length() >= 3) {
            var usuarioExiste = usuariosDb.contains(usuario.getNomeUsuario() + ":" + usuario.getSenha());
            if (usuarioExiste) {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/views/TableViewStock.fxml"));

                Scene scene = new Scene(root);

                stage.setResizable(false);
                stage.setTitle(Strings.appTitle);

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent e) {
                        Platform.exit();
                        System.exit(0);
                    }
                });

                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();

                // Ocultar tela atual
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                MeuAlert.mostrar("Erro de Login", "Usuário ou Senha inválidos!", AlertType.ERROR);
            }

        } else {
            MeuAlert.mostrar("Senha curta", "A senha deve ter no mínimo 3 caracteres", AlertType.ERROR);
        }
    }

    @FXML
    void initialize() {
        btnLogin.setDisable(true);

        tfSenha.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String senhaOld, String senhaNew) {
                if (senhaNew.length() >= 3) {
                    btnLogin.setDisable(false);
                    UtilStyles.removeErrorBorder(tfSenha);
                } else {
                    btnLogin.setDisable(true);
                    UtilStyles.setErrorBorder(tfSenha);
                }
            }
        });
    }
}
