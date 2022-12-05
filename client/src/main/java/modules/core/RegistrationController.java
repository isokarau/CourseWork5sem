package modules.core;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import modules.core.models.Employee;
import modules.core.session.Session;
import javafx.scene.control.PasswordField;


public class RegistrationController {

    @FXML
    private Button buttonConfirmRegisration;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldLogin;

    @FXML
    private TextField fieldName;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField fieldPatronymic;

    @FXML
    private TextField fieldSurname;

    private String getSurname() {
        return Trim(fieldSurname.getText());
    }
    private String getName() {
        return Trim(fieldName.getText());
    }
    private String getPatronymic() {
        return Trim(fieldPatronymic.getText());
    }
    private String getEmail() {
        return Trim(fieldEmail.getText());
    }
    private String getLogin() {
        return Trim(fieldLogin.getText());
    }
    private String getPassword() {
        return Trim(fieldPassword.getText());
    }

    @FXML
    void registrateUser(ActionEvent event) throws IOException {
        if (!Common.validateEmail(getEmail())) {
            Common.makeAlert(Alert.AlertType.WARNING, "Ошибка", "Проверьте корректность почты");
        } else if (!getSurname().isEmpty() && !getName().isEmpty() && !getPatronymic().isEmpty() 
            && !getEmail().isEmpty()  && !getLogin().isEmpty() && !getPassword().isEmpty()
            && Common.checkStr(getName()) && Common.checkStr(getSurname()) && Common.checkStr(getPatronymic())
        ) {

            System.out.println("Регистрация");
            Session.SendMessageToServer(Constants.REGISTRATION);

            Employee empl = new Employee();
            empl.setLogin(getLogin());
            empl.setPassword(getPassword());
            empl.setSurname(getSurname());
            empl.setName(getName());
            empl.setPatronymic(getPatronymic());
            empl.setEmail(getEmail());

            Session.out.writeObject(empl);
            
            String statusCode = Session.GetMessageFromServer();
            System.out.println(statusCode);

            if (statusCode.equals("OK")) {
                System.out.println("REGISTRATION OK");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Registration");
                alert.setHeaderText(null);
                alert.setContentText("Успешно");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        try {
                            Main.setRoot("fxml/start/mainWindow");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Пользователь с таким логином уже существует");
            }

        } else {
            Common.makeAlert(Alert.AlertType.ERROR, "Ошибка", "Проверьте корректность введенных данных");
        }
    }

    @FXML
    void exitInMainWindow(ActionEvent event) throws IOException {
        Main.setRoot("fxml/start/mainWindow");
    }
    
    private static String Trim(String text) {
        return text.trim();
    }

}

