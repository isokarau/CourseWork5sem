package modules.core.fxmlcontrollers.employee;

import java.io.IOException;
import java.lang.constant.Constable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.ObservableArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import modules.core.Common;
import modules.core.Constants;
import modules.core.Main;
import modules.core.models.*;
import modules.core.session.Session;
import modules.core.tables.MyReview;
import modules.core.tables.MyTraining;

public class EmployeeMenuController {

    @FXML
    private TextField commentForReceiptText;

    @FXML
    private Button confirmTrainingButton;

    @FXML
    private DatePicker dateReceiptText;

    @FXML
    private Label emailLabel;

    @FXML
    private TableView<Training> trainingTableView;

    @FXML
    private Button findButton;

    @FXML
    private DatePicker findDateButton;

    @FXML
    private Label idLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private TableColumn<MyReview, String> managerFIOColumn;

    @FXML
    private TableColumn<MyReview, Integer> markColumn;

    @FXML
    private TableColumn<MyReview, String> commentColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private TableView<MyTraining> myTrainingsTable;

    @FXML
    private TableColumn<MyTraining, String> myTrainingNameColumn;

    @FXML
    private TableColumn<MyTraining, String> myTrainingDateColumn;
    
    @FXML
    private TableColumn<MyTraining, String> myTrainingCommentColumn;

    @FXML
    private TableView<MyReview> reviewsAboutMeTable;

    @FXML
    private Label levelLabel;

    @FXML
    private TableColumn<Training, String> trainingNameColumn;

    @FXML
    private TableColumn<Training, String> trainingDateColumn;

    @FXML
    private TableColumn<Training, Integer> trainingPlaceColumn;

    ObservableList<Training> listTrainings = FXCollections.observableArrayList();
    ObservableList<MyTraining> listMyTrainings = FXCollections.observableArrayList();
    ObservableList<MyReview> listMyReviews = FXCollections.observableArrayList();

    public void initialize() {
        Session.SendMessageToServer(Constants.INITIALIZE_EMPLOYEE_WINDOW);

        Main.stage.setMaxHeight(647);
        Main.stage.setMaxWidth(910);
        Main.stage.setMinHeight(647);
        Main.stage.setMinWidth(910);

        // labels
        nameLabel.setText(Session.obj.getSurname() + " " + Session.obj.getName() + " " + Session.obj.getPatronymic());
        idLabel.setText(String.valueOf(Session.obj.getId()));
        emailLabel.setText(Session.obj.getEmail());
        loginLabel.setText(Session.obj.getLogin());
        levelLabel.setText(Session.obj.getLevel());
    
        // tables
        trainingNameColumn.setCellValueFactory(new PropertyValueFactory<Training, String>("name"));
        trainingDateColumn.setCellValueFactory(new PropertyValueFactory<Training, String>("date"));
        trainingPlaceColumn.setCellValueFactory(new PropertyValueFactory<Training, Integer>("places"));

        myTrainingNameColumn.setCellValueFactory(new PropertyValueFactory<MyTraining, String>("trainingName"));
        myTrainingDateColumn.setCellValueFactory(new PropertyValueFactory<MyTraining, String>("trainingDate"));
        myTrainingCommentColumn.setCellValueFactory(new PropertyValueFactory<MyTraining, String>("comment"));

        managerFIOColumn.setCellValueFactory(new PropertyValueFactory<MyReview, String>("managerFIO"));
        markColumn.setCellValueFactory(new PropertyValueFactory<MyReview, Integer>("mark"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<MyReview, String>("comment"));

        try {
            Session.SendMessageToServer(String.valueOf(Session.obj.getId()));
            
            //trainings
            trainingTableView.getItems().clear();
            ArrayList<Training> trainings = (ArrayList<Training>)Session.in.readObject();
            for (Training t : trainings) {
                listTrainings.add(t);
            }
            trainingTableView.setItems(listTrainings);

            // myTrainings
            myTrainingsTable.getItems().clear();
            ArrayList<MyTraining> myTrainings = (ArrayList<MyTraining>)Session.in.readObject();
            for (MyTraining t : myTrainings) {
                listMyTrainings.add(t);
            }
            myTrainingsTable.setItems(listMyTrainings);

            // myReviews
            reviewsAboutMeTable.getItems().clear();
            ArrayList<MyReview> reviewsAboutMe = (ArrayList<MyReview>)Session.in.readObject();
            for (MyReview r : reviewsAboutMe) {
                listMyReviews.add(r);
            }
            reviewsAboutMeTable.setItems(listMyReviews);

        } catch (Exception e) {
            System.out.println("Failed to initialize tables");
            e.printStackTrace();
        }

    }

    @FXML
    void changeDetails(ActionEvent event) throws IOException {
        Main.setRoot("fxml/employee/changeEmployeeDetails");
    }

    @FXML
    void comeBack(ActionEvent event) throws IOException {
        Main.setRoot("fxml/start/mainWindow");
    }

    @FXML
    void confirmTraining(ActionEvent event) {
        if (dateReceiptText.getValue() == null) {
            Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Заполните дату");
        } else {

            Session.SendMessageToServer(Constants.CONFIRM_REGISTRATION_ON_TRAINING);

            String date = dateReceiptText.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Session.SendMessageToServer(date);
            Session.SendMessageToServer(commentForReceiptText.getText());
            Session.SendMessageToServer(String.valueOf(Session.obj.getId()));
            
            String msg = Session.GetMessageFromServer();

            if (!msg.equals(Constants.SUCCESS)) {
                Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "В данный день нет доступных тренингов");
            } else {
                Common.makeAlert(Alert.AlertType.INFORMATION, "Успех", "Успешно зарегистрированы");
                initialize();
            }
        }
    }

    @FXML
    void deleteAccount(ActionEvent event) throws IOException {
        Session.SendMessageToServer(Constants.DELETE_EMPLOYEE);
        Session.SendMessageToServer(String.valueOf(Session.obj.getId()));

        Common.makeAlert(Alert.AlertType.INFORMATION, "Успех", "Аккаунт успешно удален");
        
        Main.setRoot("fxml/start/mainWindow");
    }

    @FXML
    void findTrainings(ActionEvent event) {
        if (findDateButton.getValue() != null) {
            
        } else {
            trainingTableView.setItems(listTrainings);
        }
    }

    @FXML
    void getCommentForReceiptText(ActionEvent event) {

    }

    @FXML
    void getDateReceiptText(ActionEvent event) {

    }

}
