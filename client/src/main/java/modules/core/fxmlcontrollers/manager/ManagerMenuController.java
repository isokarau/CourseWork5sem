package modules.core.fxmlcontrollers.manager;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import modules.core.Common;
import modules.core.Constants;
import modules.core.Main;
import modules.core.models.*;
import modules.core.session.Session;
import modules.core.tables.EmployeeTable;
import javafx.scene.control.cell.PropertyValueFactory;


public class ManagerMenuController {

    @FXML
    private TableColumn<?, ?> adminNameColumnRejectRequestTableView;

    @FXML
    private TextField commentForReceiptText;

    @FXML
    private TextField trainingPlaces;

    @FXML
    private Button createTrainingButton;

    @FXML
    private DatePicker dateReceiptText;

    @FXML
    private TableColumn<?, ?> definitionColumnRejectRequestTableView;

    @FXML
    private TableColumn<EmployeeTable, String> employeeFioColumn;

    @FXML
    private TableColumn<EmployeeTable, Integer> employeeIdColumn;

    @FXML
    private TableColumn<EmployeeTable, String> employeeLevelColumn;

    @FXML
    private TableColumn<EmployeeTable, String> employeeMailColumn;

    @FXML
    private TableColumn<EmployeeTable, Integer> employeeTrainingsColumn;

    @FXML
    private Label idLabel;

    @FXML
    private Label idEmployeeForDeleteLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TableColumn<?, ?> phoneNumberUserColumnRejectRequestTableView;

    @FXML
    private Button promoteEmployeeButton;


    @FXML
    private Button rateEmployeeButton;

    @FXML
    private ComboBox<String> levelChoiceBox;

    @FXML
    private TableColumn<?, ?> reasonColumnRejectRequestTableView;

    @FXML
    private TableView<?> rejectRequestTableView;

    @FXML
    private TableView<EmployeeTable> employeesTableView;

    @FXML
    private TextField employeeIdForReview;

    @FXML
    private TextField employeeMarkForReview;

    @FXML
    private TextField commentForReview;

    @FXML
    private TextField employeeIdForPromotion;

    @FXML
    private TextField employeeIdForBlock;

    @FXML
    private TableColumn<?, ?> startDateColumnRejectRequestTableView;

    @FXML
    private TableColumn<?, ?> userNameColumnRejectRequestTableView;

    ObservableList<EmployeeTable> listEmployees = FXCollections.observableArrayList();

    public void initialize() {
        Session.SendMessageToServer(Constants.INITIALIZE_MANAGER_WINDOW);

        Main.stage.setMaxHeight(647);
        Main.stage.setMaxWidth(910);
        Main.stage.setMinHeight(647);
        Main.stage.setMinWidth(910);

        // labels
        nameLabel.setText(Session.obj.getSurname() + " " + Session.obj.getName() + " " + Session.obj.getPatronymic());
        idLabel.setText(String.valueOf(Session.obj.getId()));
        emailLabel.setText(Session.obj.getEmail());
        loginLabel.setText(Session.obj.getLogin());

        // choiceBox
        levelChoiceBox.setItems(FXCollections.observableArrayList(
            "Intern",
            "Junior",
            "Middle",
            "Senior",
            "Lead")
        );

        // tables
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTable, Integer>("id"));
        employeeFioColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTable, String>("fio"));
        employeeMailColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTable, String>("email"));
        employeeLevelColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTable, String>("level"));
        employeeTrainingsColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTable, Integer>("trainings"));

        try {
            employeesTableView.getItems().clear();
            ArrayList<EmployeeTable> employees = (ArrayList<EmployeeTable>)Session.in.readObject();
            for (EmployeeTable e : employees) {
                listEmployees.add(e);
            }
            employeesTableView.setItems(listEmployees);
        } catch (Exception e) {
            System.out.println("Failed to initialize tables");
            e.printStackTrace();
        }
    }

    @FXML
    void comeBack(ActionEvent event) throws IOException {
        Main.setRoot("fxml/start/mainWindow");
    }


    String getEmployeeIdForBlock() {
        return Common.Trim(employeeIdForBlock.getText());
    }

    @FXML
    void confirmBlockEmployee(ActionEvent event) {
        if (getEmployeeIdForBlock().isEmpty() || !Common.validateNumber(getEmployeeIdForBlock())) {
            Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Проверьте корректность введенных данных");
        } else {
            Session.SendMessageToServer(Constants.FIRE_EMPLOYEE);

            Session.SendMessageToServer(String.valueOf(getEmployeeIdForBlock()));

            String statusCode = Session.GetMessageFromServer();
            if (statusCode.equals(Constants.SUCCESS)) {
                Common.makeAlert(Alert.AlertType.INFORMATION, "Успех", "Соотрудник уволен");
            } else {
                Common.makeAlert(Alert.AlertType.WARNING, "Предупреждение", "Такого сотрудника не существует");
            }
        }
    }

    @FXML
    void confirmTraining(ActionEvent event) throws IOException {
        // System.out.println("kek");
        if (getTrainingName().isEmpty() || dateReceiptText.getValue() == null || getTrainingPlaces().isEmpty() 
        || !Common.validateNumber(getTrainingPlaces())) {
            Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Проверьте корректность введенных данных");
        } else {
            Session.SendMessageToServer(Constants.CREATE_TRAINING);
            
            String date = dateReceiptText.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Training training = new Training(getTrainingName(), date, Integer.parseInt(getTrainingPlaces()));

            Session.out.writeObject(training);
            
            String statusCode = Session.GetMessageFromServer();
            System.out.println(statusCode);
            if (statusCode.equals(Constants.SUCCESS)) {
                Common.makeAlert(Alert.AlertType.INFORMATION, "Успех", "Тренинг успешно создан");
            } else {
                Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Трениг с подобным именем или датой уже существует");
            }
        }
    }
    
    String getTrainingName() {
        return Common.Trim(commentForReceiptText.getText());
    }

    String getTrainingPlaces() {
        return Common.Trim(trainingPlaces.getText());
    }

    String getEmployeeIdForReview() {
        return Common.Trim(employeeIdForReview.getText());
    }

    String getEmployeeMarkForReview() {
        return Common.Trim(employeeMarkForReview.getText());
    }

    String getCommentForReview() {
        return Common.Trim(commentForReview.getText());
    }

    String getEmployeeIdForPromotion() {
        return Common.Trim(employeeIdForPromotion.getText());
    }
    

    @FXML
    void promoteEmployee(ActionEvent event) {
        if (getEmployeeIdForPromotion().isEmpty() || !Common.validateNumber(getEmployeeIdForPromotion()) || levelChoiceBox.getSelectionModel().isEmpty()) {
            Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Заполните форму правильно");
        } else {
            Session.SendMessageToServer(Constants.PROMOTE_EMPLOYEE);

            Session.SendMessageToServer(getEmployeeIdForPromotion());
            Session.SendMessageToServer(levelChoiceBox.getValue());

            String statusCode = Session.GetMessageFromServer();
            System.out.println(statusCode);
            if (statusCode.equals(Constants.SUCCESS)) {
                Common.makeAlert(Alert.AlertType.INFORMATION, "Успех", "Уровень сотрудника успешно изменен");
                initialize();
            } else {
                Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Сотрудника с таким ID не существует");
            }
        }
    }

    @FXML
    void rateEmployee(ActionEvent event) throws IOException {
        System.out.println(getEmployeeIdForReview() + " " + getEmployeeMarkForReview() + " " + getCommentForReview());

        if (getEmployeeIdForReview().isEmpty() || getEmployeeMarkForReview().isEmpty() || getCommentForReview().isEmpty() 
        || !Common.validateNumber(getEmployeeIdForReview()) || !Common.validateNumber(getEmployeeMarkForReview()) 
        || !(Integer.parseInt(getEmployeeMarkForReview()) > 0 && Integer.parseInt(getEmployeeMarkForReview()) <= 5)) {
            Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Проверьте корректность введенных данных");
        } else {
            Session.SendMessageToServer(Constants.CREATE_REVIEW);

            int employeeId = Integer.parseInt(getEmployeeIdForReview());
            int mark = Integer.parseInt(getEmployeeMarkForReview());
            Review review = new Review(employeeId, Session.obj.getId(), mark, getCommentForReview());

            Session.out.writeObject(review);

            String statusCode = Session.GetMessageFromServer();
            System.out.println(statusCode);
            if (statusCode.equals(Constants.SUCCESS)) {
                Common.makeAlert(Alert.AlertType.INFORMATION, "Успех", "Отзыв успешно создан");
            }
            
        }
    }

    @FXML
    void deleteAccount(ActionEvent event) {

    }

    @FXML
    void sendStatistic(ActionEvent event) {

    }

    @FXML
    void showDiagram(ActionEvent event) {

    }

    @FXML
    void showGraphic(ActionEvent event) {

    }

}
