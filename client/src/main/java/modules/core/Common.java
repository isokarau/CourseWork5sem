package modules.core;

import java.util.regex.*;

import javafx.scene.control.Alert;

public class Common {
    public static void makeAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean validateEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateNumber(String number) {
        String regexPattern = "^[0-9]+$";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static String Trim(String text) {
        return text.trim();
    }

    public static boolean checkStr(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i)) && str.charAt(i) != ' ')
                return false;
        }
        return true;
    }
}
