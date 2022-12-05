package modules.core.controllers;

import java.sql.*;

import modules.core.database.Database;
import modules.core.models.Manager;

public class ManagerController {
    public static Manager getManager(String login, String password) {
        String getEmployee = "SELECT * FROM manager WHERE login = '%s' AND password = '%s'".formatted(login, password);

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet res = statement.executeQuery(getEmployee);
            if (res.next()) {
                int id = res.getInt("manager_id");
                String surname = res.getString("surname");
                String name = res.getString("name");
                String patronymic = res.getString("patronymic");
                String email = res.getString("email");
                return new Manager(id, login, password, surname, name, patronymic, email);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get manager");
        }

        return null;
    }

    public static Manager getManagerById(int id) {
        String getEmployeeById = "SELECT * FROM manager WHERE manager_id = '%d'".formatted(id);

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet res = statement.executeQuery(getEmployeeById);
            if (res.next()) {
                String login = res.getString("login");
                String password = res.getString("password");
                String surname = res.getString("surname");
                String name = res.getString("name");
                String patronymic = res.getString("patronymic");
                String email = res.getString("email");
                return new Manager(id, login, password, surname, name, patronymic, email);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get employee by id");
        }

        return null;
    }

    public static boolean createManager(Manager m) {
        String insertEmployee = "INSERT INTO manager(login, password, surname, name, patronymic, email) "
        + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')".formatted(m.getLogin(), m.getPassword(), m.getSurname(), m.getName(), 
        m.getPatronymic(), m.getEmail());
        try {
            Statement statement = Database.getConnection().createStatement();
            int rows = statement.executeUpdate(insertEmployee);
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert manager");
        }

        return false;
    }
}
