package modules.core.controllers;

import java.sql.*;
import java.util.ArrayList;

import modules.core.database.Database;
import modules.core.models.Employee;
import modules.core.tables.*;

public class EmployeeController {
    public static Employee getEmployee(String login, String password) {
        String getEmployee = "SELECT * FROM employee WHERE login = '%s' AND password = '%s'".formatted(login, password);

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet res = statement.executeQuery(getEmployee);
            if (res.next()) {
                int id = res.getInt("empl_id");
                String surname = res.getString("surname");
                String name = res.getString("name");
                String patronymic = res.getString("patronymic");
                String email = res.getString("email");
                String level = res.getString("level");
                int trainings = res.getInt("trainings");
                return new Employee(id, login, password, surname, name, patronymic, email, level, trainings);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get employee");
        }

        return null;
    }
    
    public static Employee getEmployeeById(int id) {
        String getEmployeeById = "SELECT * FROM employee WHERE empl_id = '%d'".formatted(id);

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
                String level = res.getString("level");
                int trainings = res.getInt("trainings");
                return new Employee(id, login, password, surname, name, patronymic, email, level, trainings);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get employee by id");
        }

        return null;
    }

    public static ArrayList<EmployeeTable> getAll() {
        String getEmployeeById = "SELECT * FROM employee";

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet res = statement.executeQuery(getEmployeeById);
            ArrayList<EmployeeTable> employees = new ArrayList<EmployeeTable>();
            while (res.next()) {
                int id = res.getInt("empl_id");
                String surname = res.getString("surname");
                String name = res.getString("name");
                String patronymic = res.getString("patronymic");
                String email = res.getString("email");
                String level = res.getString("level");
                int trainings = res.getInt("trainings");
                employees.add(new EmployeeTable(id, surname + " " + name + " " + patronymic, email, level, trainings));
            }

            return employees;
        } catch (SQLException e) {
            System.out.println("Failed to get all");
        }

        return null;
    }

    public static boolean createEmployee(Employee empl) {
        String insertEmployee = "INSERT INTO employee(login, password, surname, name, patronymic, email) "
        + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')".formatted(empl.getLogin(), empl.getPassword(), empl.getSurname(), empl.getName(), 
        empl.getPatronymic(), empl.getEmail());
        try {
            Statement statement = Database.getConnection().createStatement();
            int rows = statement.executeUpdate(insertEmployee);
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert employee");
        }

        return false;
    }

    public static boolean updateEmployee(Employee empl) {

        String insertEmployee = "UPDATE employee SET login = '%s', password = '%s', surname = '%s', name = '%s', patronymic = '%s', "
        + "email = '%s', level = '%s', trainings = '%d' WHERE empl_id = '%d'";
        insertEmployee = insertEmployee.formatted(empl.getLogin(), empl.getPassword(), empl.getSurname(), empl.getName(), 
        empl.getPatronymic(), empl.getEmail(), empl.getLevel(), empl.getCompletedTrainings(), empl.getId());

        // System.out.println(insertEmployee);
        try {
            Statement statement = Database.getConnection().createStatement();
            int rows = statement.executeUpdate(insertEmployee);
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert employee");
        }

        return false;
    }

    public static boolean promoteEmployee(int emplId, String newLevel) {

        String promoteEmployee = "UPDATE employee SET level = '%s' WHERE empl_id = '%d'".formatted(newLevel, emplId);

        try {
            Statement statement = Database.getConnection().createStatement();
            int rows = statement.executeUpdate(promoteEmployee);
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to promote employee");
        }

        return false;
    }

    public static void deleteAccount(int id) {
        String insertEmployee = "DELETE FROM employee WHERE empl_id = '%d'".formatted(id);
        try {
            Statement statement = Database.getConnection().createStatement();
            statement.executeUpdate(insertEmployee);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert employee");
        }
    }

}
