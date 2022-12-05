package modules.core.fxmlcontrollers.employee;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modules.core.Common;
import modules.core.Constants;
import modules.core.Main;
import modules.core.models.Employee;
import modules.core.models.Manager;
import modules.core.session.Session;
import javafx.scene.control.Alert;

public class ChangeEmployeeDetails {

    @FXML
    private Button closeButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField patronymicField;

    @FXML
    private TextField surnameField;

    private String Trim(String text) {
        return text.trim();
    }

    private String getSurname() {
        return Trim(surnameField.getText());
    }
    private String getName() {
        return Trim(nameField.getText());
    }
    private String getPatronymic() {
        return Trim(patronymicField.getText());
    }
    private String getEmail() {
        return Trim(emailField.getText());
    }
    private String getLogin() {
        return Trim(loginField.getText());
    }
    private String getPassword() {
        return Trim(passwordField.getText());
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Main.stage.setMaxHeight(450);
        Main.stage.setMaxWidth(370);
        Main.stage.setMinHeight(450);
        Main.stage.setMinWidth(370);

        Manager employee = Session.obj;
        nameField.setText(employee.getName());
        surnameField.setText(employee.getSurname());
        patronymicField.setText(employee.getPatronymic());
        loginField.setText(employee.getLogin());
        emailField.setText(employee.getEmail());
        passwordField.setText(employee.getPassword());
    }

    @FXML
    void close(ActionEvent event) throws IOException {
        Main.setRoot("fxml/employee/employeeMenu");
    }

    @FXML
    void confirmChanges(ActionEvent event) throws IOException, ClassNotFoundException {
        if (getSurname().isEmpty() || getName().isEmpty() || getPatronymic().isEmpty() 
            || getEmail().isEmpty() || getLogin().isEmpty() || getPassword().isEmpty()
            || !Common.checkStr(getName()) || !Common.checkStr(getSurname()) || !Common.checkStr(getPatronymic())
        ) {
            Common.makeAlert(Alert.AlertType.WARNING, "Ошибка", "Проверьте корректность введенных данных");
        } else if (!Common.validateEmail(getEmail())) {
            Common.makeAlert(Alert.AlertType.WARNING, "Ошибка", "Проверьте корректность почты");
        } else {

            Session.SendMessageToServer(Constants.CHANGE_EMPLOYEE_INFO);
            Employee empl = new Employee(Session.obj.getId(), getLogin(), getPassword(), getSurname(), getName(), getPatronymic(), 
                getEmail(), Session.obj.getLevel(), Session.obj.getCompletedTrainings());
            Session.out.writeObject((Employee)empl);
            
            String msg = Session.GetMessageFromServer();
            System.out.println("Message = " + msg);
            if (msg.equals(Constants.SUCCESS)) {
                empl = (Employee)Session.in.readObject();
                System.out.println(empl.getName());
                Session.obj = empl;
                Main.setRoot("fxml/employee/employeeMenu");
            } else {
                Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Проверьте корректность введенных данных");
            }
        }
    }

}
