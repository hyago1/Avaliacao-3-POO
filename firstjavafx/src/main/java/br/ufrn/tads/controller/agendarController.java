package br.ufrn.tads.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import br.ufrn.tads.App;
import br.ufrn.tads.model.Horarios;
import br.ufrn.tads.service.HorariosService;
import br.ufrn.tads.service.UserService;
import br.ufrn.tads.service.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class agendarController {

    @FXML
    private ImageView userImage;

    @FXML
    private Label userName;

    @FXML
    private ListView list;

    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnConfirmar;

    @FXML
    void initialize() throws SQLException {
        Image userImg = new Image("user.png");

        userImage.setImage(userImg);
        List<Horarios> hr = buscarHorarios();
        
        for (Horarios horarios : hr) {
            System.out.println(horarios.getVago());
            if (!horarios.getVago()) {
                
                list.getItems().add(horarios.getHora());
            }
        }
        String nome = UserSession.getInstance().getUserName();
        userName.setText(nome);

    }

    public List<Horarios> buscarHorarios() throws SQLException {
        HorariosService horarios = new HorariosService();
        return horarios.buscarHorarios();

    }

    public void confirmar() throws SQLException, IOException {
        HorariosService horariosService = new HorariosService();
        Object itemSelecionado = list.getSelectionModel().getSelectedItem();
        String hora = itemSelecionado.toString();
         Time horaConvertida = Time.valueOf(hora);

         System.out.println(horaConvertida);
        List<Horarios> hr = buscarHorarios();
        for (Horarios horarios : hr) {
           
            if (horarios.getHora().equals(horaConvertida)) {
                horariosService.confirmar(horarios);
            }
        }
        
        App.setRoot("index");
        System.out.println(hora);
    }

    public void telaIndex() throws IOException {
        App.setRoot("index");
    }

}
