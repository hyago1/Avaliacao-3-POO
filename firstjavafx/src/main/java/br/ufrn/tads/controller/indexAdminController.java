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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class indexAdminController {
        @FXML
    private MenuButton day;
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
    private  List<Horarios> hr;
    private String itemDay = "Seg";
    @FXML
    void initialize() throws SQLException {

        Image userImg = new Image("user.png");
        userImage.setImage(userImg);
        hr = hrService.buscarHorariosParaTabelaDoAdmin(itemDay);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("agendadoPor"));
      
        
        ObservableList<Horarios> list = FXCollections.observableArrayList(hr);
          for (MenuItem item : day.getItems()) {
                    item.setOnAction(e ->     {
                        try {
                            hr =  hrService.buscarHorariosParaTabelaDoAdmin(item.getText());
                            itemDay = item.getText();
                            atualizarList();
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    });
                }
        tableAdmin.setItems(list);
        
        // for (Horarios horarios : hr) {
        //     System.out.println(horarios + " " + horarios.getHora() + " " + horarios.getAgendadoPor());
        // }

        String nome = UserSession.getInstance().getUserName();
        userName.setText(nome);

    }
    

    public void atualizarList(){
        ObservableList<Horarios> list = FXCollections.observableArrayList(hr);
        tableAdmin.getItems().clear();
        

            tableAdmin.setItems(list);
        
    
       
    }

    @FXML
    public void loggof() throws IOException {
        UserSession.getInstance().loggof();
        App.setRoot("login");
    }
    public void cancelarHorario() throws IOException, SQLException {
    hrService.cancelarHorario(tableAdmin.getSelectionModel().getSelectedItem(), itemDay);
      initialize();
        
    }

    

}