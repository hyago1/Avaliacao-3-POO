package br.ufrn.tads.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import br.ufrn.tads.App;
import br.ufrn.tads.service.UserService;
import br.ufrn.tads.service.UserSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class loginController {

    @FXML
    private Button btnUser;

    @FXML
    private Button btnUser1;

    @FXML
    private TextField cpfUser;

    @FXML
    private PasswordField pwUser;

    @FXML
    private Label msgStatus;

    @FXML

    private void TelaCadastrar() throws IOException {

        App.setRoot("cadastro");
    }

    @FXML
    private void loginUser() throws IOException, SQLException {
        UserService userService = new UserService();
        String senha = pwUser.getText();
        Long cpf = null;
        if (cpfUser.getText() != "") {
            cpf = Long.parseLong(cpfUser.getText());
        }
        if (!senha.isEmpty() && cpf != null) {
            String nome = userService.autenticarUsuario(cpf, senha);
            if (nome != null) {
                UserSession.getInstance().setUserName(nome);
                if (UserSession.getInstance().getId() == 1) {
                    App.setRoot("indexAdmin");
                } else {

                    App.setRoot("index");
                }
            } else {
            }

            setMsg("CPF ou Senha inválidos");
            msgStatus.setTextFill(Color.RED);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setMsg("Login");
                    msgStatus.setTextFill(Color.rgb(116, 116, 116));

                }
            }, 2000);
        }

    }

    public void setMsg(String menssagem) {
        if (msgStatus != null) {
            Platform.runLater(() -> {
                msgStatus.setText(menssagem);
            });

        } else {
            System.out.println("Erro: o Label 'msg' não foi inicializado!");
        }

    }

}
