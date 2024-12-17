package br.ufrn.tads.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import br.ufrn.tads.App;
import br.ufrn.tads.repository.UserDao;
import br.ufrn.tads.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class cadastroController {

    @FXML
    private Button btnUser;

    @FXML
    private Button btnUser1;

    @FXML
    private TextField cpfUser;

    @FXML
    private TextField nameUser;

    @FXML
    private Label msgStatus;

    @FXML
    private PasswordField pwUser;

    @FXML
    public void initialize() {
  
nameUser.deselect();
pwUser.deselect();

    }

    @FXML
    private void TelaLogin() throws IOException {

        App.setRoot("login");
    }

    @FXML
    private void cadastrarUsuario() throws IOException, SQLException {
        Timer timer = new Timer();
        UserService userService = new UserService();
        String nome = nameUser.getText();
        String senha = pwUser.getText();
        Long cpf = null;
        if (cpfUser.getText() != "") {

            cpf = Long.parseLong(cpfUser.getText());
        }

        System.out.println(nome);
        if ((nome != "" && senha != "" && cpfUser.getText() != "")) {
            Boolean temCpf = userService.verificarCpfExistente(cpf);

            if (!temCpf) {
                userService.saveUser(nome, cpf, senha);

            } else {

                setMsg("Este usuario já existe");
                msgStatus.setTextFill(Color.RED);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setMsg("Cadastrar Usuario:");
                        msgStatus.setTextFill(Color.BLACK);

                    }
                }, 2000);
            }
        } else {
            setMsg("Digite todos os dados:");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setMsg("Cadastrar Usuario:");
                    msgStatus.setTextFill(Color.BLACK);

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
