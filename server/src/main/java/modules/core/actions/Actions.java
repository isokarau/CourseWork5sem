package modules.core.actions;

import java.io.IOException;
import java.util.ArrayList;

import com.mysql.cj.Session;

import modules.core.Constants;
import modules.core.controllers.*;
import modules.core.models.*;
import modules.core.server.*;
import modules.core.tables.*;

public class Actions {
    private Connection con;
    
    public Actions(Connection con) {
        this.con = con;
    }

    public void go() {
        String line;
        while (true) {
            line = con.GetMessageFromClient();
            System.out.printf("Sent from client: %s\n", line);
            if (line.equals(Constants.ENTER)) {
                Authorization();
            } else if (line.equals(Constants.REGISTRATION)) {
                Registration();
            } else if (line.equals(Constants.CHANGE_EMPLOYEE_INFO)) {
                ChangeEmployeeInfo();
            } else if (line.equals(Constants.DELETE_EMPLOYEE)) {
                DeleteEmployee();
            } else if (line.equals(Constants.CONFIRM_REGISTRATION_ON_TRAINING)) {
                ConfirmRegistrationOnTraining();
            } else if (line.equals(Constants.INITIALIZE_EMPLOYEE_WINDOW)) {
                InitializeEmployeeWindow();
            } else if (line.equals(Constants.CREATE_TRAINING)) {
                CreateTraining();
            } else if (line.equals(Constants.CREATE_REVIEW)) {
                CreateReview();
            } else if (line.equals(Constants.PROMOTE_EMPLOYEE)) {
                PromoteEmployee();
            } else if (line.equals(Constants.INITIALIZE_MANAGER_WINDOW)) {
                InitializeManagerWindow();
            } else if (line.equals(Constants.FIRE_EMPLOYEE)) {
                FireEmployee();
            } 
            // TODO: handle invalid request
        }
    }

    public void Authorization() {
        String login = con.GetMessageFromClient();
        String password = con.GetMessageFromClient();
        System.out.println(login + " " + password);

        Employee e = EmployeeController.getEmployee(login, password);
        Manager m = ManagerController.getManager(login, password);

        System.out.println(e == null);

        if (e != null) {
            con.SendMessageToClient(Constants.EMPLOYEE);
            try {
                // System.out.println(e.getName() + " SUKA " + e.getSurname());
                con.out.writeObject(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }        
        } else if (m != null) {
            con.SendMessageToClient(Constants.MANAGER);
            try {
                con.out.writeObject(m);
            } catch (IOException ex) {
                ex.printStackTrace();
            } 
        } else {
            con.SendMessageToClient(Constants.FAILURE);
        }
    }

    public void Registration() {
        try {
            Employee empl = (Employee)con.in.readObject();
            System.out.println(empl.getName() + " " + empl.getSurname());
            if (EmployeeController.createEmployee(empl)) {
                con.SendMessageToClient(Constants.SUCCESS);
            } else {
                con.SendMessageToClient(Constants.FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChangeEmployeeInfo() {
        try {
            Employee empl = (Employee)con.in.readObject();
            
            if (EmployeeController.updateEmployee(empl)) {
                con.SendMessageToClient(Constants.SUCCESS);
                empl = EmployeeController.getEmployeeById(empl.getId());
                System.out.println(empl.getName());
                con.out.writeObject(empl);
            } else {
                con.SendMessageToClient(Constants.FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteEmployee() {
        int id = Integer.parseInt(con.GetMessageFromClient());
        EmployeeController.deleteAccount(id);
    }

    public void ConfirmRegistrationOnTraining() {
        String date = con.GetMessageFromClient();
        String comment = con.GetMessageFromClient();
        String emplId = con.GetMessageFromClient();

        System.out.println(date + "    " + comment + "    " + emplId);

        if (TrainingRecordsController.registrateEmployeeOnTraining(date, Integer.parseInt(emplId), comment)) {
            con.SendMessageToClient(Constants.SUCCESS);
        } else {
            con.SendMessageToClient(Constants.FAILURE);
        }
    }

    public void InitializeEmployeeWindow() {
        int emplId = Integer.parseInt(con.GetMessageFromClient()); // get empl id
        System.out.println(emplId);

        ArrayList<Training> trainings = TrainingController.getAllTrainings();
        ArrayList<MyTraining> myTrainings = TrainingRecordsController.getEmployeeTrainings(emplId);
        ArrayList<MyReview> myReviews = ReviewsController.getEmployeeReviews(emplId);
        System.out.println(myReviews.size());
        try {
            con.out.writeObject(trainings);
            con.out.writeObject(myTrainings);
            con.out.writeObject(myReviews);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InitializeManagerWindow() {
        ArrayList<EmployeeTable> employees = EmployeeController.getAll();
        try {
            con.out.writeObject(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CreateTraining() {
        try {
            Training training = (Training)con.in.readObject();
            if (TrainingController.createTraining(training)) {
                System.out.println("Training created");
                con.SendMessageToClient(Constants.SUCCESS);
            } else {
                con.SendMessageToClient(Constants.FAILURE);
            }
            // System.out.println(training.getName() + "  " + training.getPlaces());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CreateReview() {
        try {
            Review review = (Review)con.in.readObject();
            if (ReviewsController.createReview(review)) {
                con.SendMessageToClient(Constants.SUCCESS);
            } else {
                con.SendMessageToClient(Constants.FAILURE);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void PromoteEmployee() {
        int emplId = Integer.parseInt(con.GetMessageFromClient());
        String newLevel = con.GetMessageFromClient();

        // con.SendMessageToClient(Constants.SUCCESS);

        if (EmployeeController.promoteEmployee(emplId, newLevel)) {
            con.SendMessageToClient(Constants.SUCCESS);
        } else {
            con.SendMessageToClient(Constants.FAILURE);
        }
    }

    public void FireEmployee() {
        int emplId = Integer.parseInt(con.GetMessageFromClient());
        System.out.println(emplId);

        con.SendMessageToClient(Constants.SUCCESS);
    }
}
