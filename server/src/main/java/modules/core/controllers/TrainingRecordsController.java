package modules.core.controllers;

import java.sql.*;
import java.util.ArrayList;

import modules.core.database.Database;
import modules.core.models.*;
import modules.core.tables.MyTraining;

public class TrainingRecordsController {
    public static boolean registrateEmployeeOnTraining(String trainingDate, int emplId, String comment) {
        String trainingName = TrainingController.getTrainingByDate(trainingDate);
        if (TrainingController.updatePlaces(trainingDate)) {

            System.out.println(trainingName);

            String addRecord = "INSERT INTO training_records(name, empl_id, comment) "
            + "VALUES ('%s', '%d', '%s')".formatted(trainingName, emplId, comment);

            try {
                Statement statement = Database.getConnection().createStatement();
                int rows = statement.executeUpdate(addRecord);
                if (rows == 1) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to registrate employee on training");
            }

            return false;
        }

        return false;
    }

    public static ArrayList<MyTraining> getEmployeeTrainings(int emplId) {
        String getEmployeeTrainings = "SELECT trainings.name, trainings.date, training_records.comment FROM training_records INNER JOIN trainings ON training_records.name = trainings.name AND empl_id = '%d'".formatted(emplId);

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet res = statement.executeQuery(getEmployeeTrainings);
            ArrayList<MyTraining> trainings = new ArrayList<MyTraining>();
            while (res.next()) {
                String name = res.getString("name");
                String date = res.getString("date");
                String comment = res.getString("comment");
                // System.out.println(name + " " + date + " " + comment);
                trainings.add(new MyTraining(name, date, comment));
            }
            
            return trainings;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get employee trainings");
        }

        return null;
    }
}
