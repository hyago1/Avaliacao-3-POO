
package br.ufrn.tads.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.tads.App;
import br.ufrn.tads.model.Horarios;
import br.ufrn.tads.service.HorariosService;
import br.ufrn.tads.service.UserService;
import br.ufrn.tads.service.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class agendarController {

    @FXML
    private MenuButton day;

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
    private List<Horarios> hr;
    private int itemDay = 1;

    @FXML
    void initialize() throws SQLException {
        Map<String, Integer> diasDaSemana = new HashMap<>();
        diasDaSemana.put("Seg", 1);
        diasDaSemana.put("Ter", 2);
        diasDaSemana.put("Qua", 3);
        diasDaSemana.put("Qui", 4);
        diasDaSemana.put("Sex", 5);
        diasDaSemana.put("Sab", 6);

        buscarHorarios(itemDay);
        atualizarList();
        for (MenuItem item : day.getItems()) {

            item.setOnAction(e -> {
                try {
                    itemDay = diasDaSemana.get(item.getText());
                    buscarHorarios(itemDay);
                    System.out.println("vvvvvvvvvvvvv " +itemDay );
                    day.setText(item.getText());
                    atualizarList();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
        }
        Image userImg = new Image("user.png");
        userImage.setImage(userImg);

        String nome = UserSession.getInstance().getUserName();
        userName.setText(nome);

    }

    public void atualizarList() {
        list.getItems().clear();
        for (Horarios horarios : hr) {

            list.getItems().add(horarios.getHora());

        }

    }

    public List<Horarios> buscarHorarios(int d) throws SQLException {
        HorariosService horarios = new HorariosService();
    System.out.println("Chegou no buscarHorarios() " + d );
        hr = horarios.buscarHorariosPeloDia(d);
        return hr;

    }

    public void confirmar() throws SQLException, IOException {
        HorariosService horariosService = new HorariosService();
        Object itemSelecionado = list.getSelectionModel().getSelectedItem();
        String hora = itemSelecionado.toString();
        Time horaConvertida = Time.valueOf(hora);

        System.out.println(horaConvertida);

        for (Horarios horarios : hr) {
            if (horarios.getHora().equals(horaConvertida)) {
                horariosService.confirmar(horarios, itemDay);
            }
        }

        App.setRoot("index");
        System.out.println(hora);
    }

    public void telaIndex() throws IOException {
        App.setRoot("index");
    }

}
