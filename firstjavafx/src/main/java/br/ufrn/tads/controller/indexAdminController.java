package br.ufrn.tads.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import br.ufrn.tads.App;
import br.ufrn.tads.model.Horarios;
import br.ufrn.tads.model.Product;
import br.ufrn.tads.service.HorariosService;
import br.ufrn.tads.service.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class indexAdminController {

    @FXML
    private TableView<Horarios> tableAdmin;

    @FXML
    private TableColumn<Horarios, Integer> colId; // Coluna ID
    @FXML
    private TableColumn<Horarios, Time> colHora; // Coluna Hora
    @FXML
    private TableColumn<Horarios, String> colResponsavel; // Coluna Responsável
    @FXML
    private TableColumn<Horarios, Void> acaoColumn; // Coluna Responsável

    private ObservableList<Horarios> horariosList;
    @FXML
    private ImageView userImage;

    @FXML
    private Label userName;
    HorariosService hrService = new HorariosService();
    @FXML
    void initialize() throws SQLException {

        Image userImg = new Image("user.png");
        userImage.setImage(userImg);
        List<Horarios> hr = hrService.buscarHorariosParaTabelaDoAdmin();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("agendadoPor"));
      
        
        ObservableList<Horarios> list = FXCollections.observableArrayList(hr);

        tableAdmin.setItems(list);
        
        for (Horarios horarios : hr) {
            System.out.println(horarios + " " + horarios.getHora() + " " + horarios.getAgendadoPor());
        }

        String nome = UserSession.getInstance().getUserName();
        userName.setText(nome);

    }

    @FXML
    public void loggof() throws IOException {
        UserSession.getInstance().loggof();
        App.setRoot("login");
    }
    public void cancelarHorario() throws IOException, SQLException {
    hrService.cancelarHorario(tableAdmin.getSelectionModel().getSelectedItem());
      initialize();
        
    }

    

}