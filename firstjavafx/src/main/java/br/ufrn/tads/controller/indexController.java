package br.ufrn.tads.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import br.ufrn.tads.App;
import br.ufrn.tads.model.Horarios;
import br.ufrn.tads.service.HorariosService;
import br.ufrn.tads.service.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class indexController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView userHorarioList;

    @FXML
    private Button reservar;

    @FXML
    private  Label userName;
    @FXML
    private ImageView userImage;
    @FXML
    private ImageView ballImage;
    @FXML
    void initialize() throws SQLException {
        HorariosService hrService = new HorariosService();
        Image userImg = new Image("user.png");
        Image ballImg = new Image("bola.png");
        userImage.setImage(userImg);
        ballImage.setImage(ballImg);
        String nome = UserSession.getInstance().getUserName();
        userName.setText(nome); 
        List<Horarios> hr = hrService.buscarHorariosId();
        System.out.println(hr);
        for (Horarios horarios : hr) {
                userHorarioList.getItems().add(horarios.getHora());
        }
        }

        public void telaAgendar() throws IOException{
                App.setRoot("telaAgendar");
        }
     


}
