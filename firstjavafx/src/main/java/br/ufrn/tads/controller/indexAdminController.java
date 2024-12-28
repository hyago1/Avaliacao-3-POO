package br.ufrn.tads.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TableView<Horarios> tableAdmin2;
    @FXML
    private TableColumn<Horarios, Time> colHour;
    @FXML
    private TableColumn<Horarios, Integer> colId2;
    @FXML
    private TableColumn<Horarios, Integer> colId; // Coluna ID
    @FXML
    private TableColumn<Horarios, Time> colHora; // Coluna Hora
    @FXML
    private TableColumn<Horarios, String> colResponsavel; // Coluna Responsável
    @FXML
    private TableColumn<Horarios, String> colDia; // Coluna Dia
    @FXML
    private TableColumn<Horarios, Void> acaoColumn; // Coluna Responsável

    private ObservableList<Horarios> horariosList;
    @FXML
    private ImageView userImage;

    @FXML
    private Label userName;
    HorariosService hrService = new HorariosService();
    private List<Horarios> hr;
    private List<Horarios> hr2;
    private Integer itemDay = 1;

    @FXML
    void initialize() throws SQLException {
        Map<String, Integer> diasDaSemana = new HashMap<>();
        diasDaSemana.put("Seg", 1);
        diasDaSemana.put("Ter", 2);
        diasDaSemana.put("Qua", 3);
        diasDaSemana.put("Qui", 4);
        diasDaSemana.put("Sex", 5);
        diasDaSemana.put("Sab", 6);

        Image userImg = new Image("user.png");
        userImage.setImage(userImg);
        hr = hrService.hrAgendadosParaTabelaDoAdmin(itemDay);
        hr2 = hrService.buscarHorarios();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId2.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colDia.setCellValueFactory(new PropertyValueFactory<>("diaDele"));
        colHour.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("agendadoPor"));

        ObservableList<Horarios> list = FXCollections.observableArrayList(hr);
        ObservableList<Horarios> list2 = FXCollections.observableArrayList(hr2);
        for (MenuItem item : day.getItems()) {
            item.setOnAction(e -> {
                try {
                    itemDay = diasDaSemana.get(item.getText());
                    hr = hrService.hrAgendadosParaTabelaDoAdmin(itemDay);
                    atualizarList();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
        }
        
        tableAdmin.setItems(list);
        tableAdmin2.setItems(list2);
        
        String nome = UserSession.getInstance().getUserName();
        userName.setText(nome);
        
    }
    
    public void atualizarList() {
        ObservableList<Horarios> list2 = FXCollections.observableArrayList(hr2);
        ObservableList<Horarios> list = FXCollections.observableArrayList(hr);
        tableAdmin.getItems().clear();
        tableAdmin.setItems(list);
        tableAdmin2.getItems().clear();
        tableAdmin2.setItems(list2);

    }

    @FXML
    public void loggof() throws IOException {
        UserSession.getInstance().loggof();
        App.setRoot("login");
    }

    public void novoHorario(){
        
    }
    public void excluirHorario() throws SQLException{
        Horarios n = tableAdmin2.getSelectionModel().getSelectedItem();
        hrService.deletarHorario(n);
        initialize();
    }

    public void cancelarHorario() throws IOException, SQLException {
        hrService.cancelarHorario(tableAdmin.getSelectionModel().getSelectedItem(), itemDay);
        initialize();

    }
}