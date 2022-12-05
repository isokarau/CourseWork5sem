package modules.core;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modules.core.models.*;
import modules.core.session.Session;


public class AccountEntrance {
    @FXML
    private Button buttonRegister;

    @FXML
    private Button buttonSignIn;

    @FXML
    private TextField textFieldLogin;

    @FXML
    private PasswordField textFieldPassword;

    public void initialize() {
        Main.stage.setMaxHeight(430);
        Main.stage.setMaxWidth(600);
        Main.stage.setMinHeight(430);
        Main.stage.setMinWidth(600);
    }

    @FXML
    void buttonEnterAction(ActionEvent event) throws IOException, ClassNotFoundException {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();

        Session.SendMessageToServer("ENTER");
        Session.SendMessageToServer(login);
        Session.SendMessageToServer(password);

        String statusCode = Session.GetMessageFromServer();
        System.out.println(statusCode);
        
        if (statusCode.equals(Constants.EMPLOYEE)) {
            System.out.println("EMPLOYEE");
            Employee empl = (Employee)Session.in.readObject();
            Session.obj = empl;

            System.out.println(Session.obj.getName());
            System.out.println(Session.obj.getLevel());
            
            Main.setRoot("fxml/employee/employeeMenu");
        } else if (statusCode.equals(Constants.MANAGER)) {
            System.out.println("MANAGER");
            Manager m = (Manager)Session.in.readObject();
            Session.obj = m;
            Main.setRoot("fxml/manager/managerMenu");
        } else {
            textFieldLogin.clear();
            textFieldPassword.clear();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Ошибка");
            a.setHeaderText(null);
            a.setContentText("Некорректный ввод");
            a.show();
        }
    }

    @FXML
    void buttonRegisterAction(ActionEvent event) throws IOException {
        Main.setRoot("fxml/start/registration");
    }

}
