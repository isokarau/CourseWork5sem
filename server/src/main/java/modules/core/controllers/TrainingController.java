package modules.core.controllers;

import java.sql.*;
import java.util.ArrayList;

import modules.core.database.Database;
import modules.core.models.Training;

public class TrainingController {
    public static ArrayList<Training> getAllTrainings() {
        String getAllTrainings = "SELECT * FROM trainings";

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet res = statement.executeQuery(getAllTrainings);
            ArrayList<Training> trainings = new ArrayList<Training>();
            while (res.next()) {
                String name = res.getString("name");
                String date = res.getString("date");
                int places = res.getInt("places");
                // System.out.println(name + " " + date + " " + places);
                trainings.add(new Training(name, date, places));
            }
            
            return trainings;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get trainings");
        }

        return null;
    }

    public static String getTrainingByDate(String date) {
        String getTrainingByDate = "SELECT * FROM trainings WHERE date = '%s'".formatted(date);

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet res = statement.executeQuery(getTrainingByDate);
            System.out.println(date);
            while (res.next()) {
                String name = res.getString("name");
                return name;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get trainings");
        }

        return null;
    }

    public static boolean createTraining(Training training) {
        String addRecord = "INSERT INTO trainings(name, date, places) "
        + "VALUES ('%s', '%s', '%d')".formatted(training.getName(), training.getDate(), training.getPlaces());

        try {
            Statement statement = Database.getConnection().createStatement();
            int rows = statement.executeUpdate(addRecord);
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create training");
        }

        return false;
    }

    // public static void deleteEmptyTrainings() {
    //     String deleteTrainings = "DELETE FROM trainings WHERE places = 0";

    //     try {
    //         Statement statement = Database.getConnection().createStatement();
    //         statement.executeUpdate(deleteTrainings);
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         System.out.println("Failed to delete empty trainings");
    //     }
    // }

    public static boolean updatePlaces(String trainingDate) {

        String updateTrainig = "UPDATE trainings SET places = places - 1 WHERE places > 0 AND date = '%s'".formatted(trainingDate);

        try {
            Statement statement = Database.getConnection().createStatement();
            int rows = statement.executeUpdate(updateTrainig);
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update places");
        }

        return false;
    }
}
