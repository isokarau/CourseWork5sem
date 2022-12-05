package modules.core.controllers;

import java.sql.*;
import java.util.ArrayList;

import modules.core.database.Database;
import modules.core.models.*;
import modules.core.tables.MyReview;

public class ReviewsController {

    public static ArrayList<MyReview> getEmployeeReviews(int emplId) {
        String getEmployeeReview = "SELECT * FROM reviews WHERE empl_id = '%d'".formatted(emplId);

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet res = statement.executeQuery(getEmployeeReview);
            ArrayList<MyReview> reviews = new ArrayList<MyReview>();
            while (res.next()) {
                int managerId = res.getInt("manager_id");
                int mark = res.getInt("mark");
                String comment = res.getString("comment");

                Manager m = ManagerController.getManagerById(managerId);
                String managerFio = m.getSurname() + " " + m.getName() + " " + m.getPatronymic();
                System.out.println("MANAGER FIO = " + managerFio);
                // System.out.println(name + " " + date + " " + places);
                reviews.add(new MyReview(managerFio, mark, comment));
            }

            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert employee");
        }

        return null;
    }

    public static boolean createReview(Review review) {
        String createReview = "INSERT INTO reviews(empl_id, manager_id, mark, comment) VALUES " + 
        "('%d', '%d', '%d', '%s')".formatted(review.getEmployeeId(), review.getManagerId(), review.getMark(), review.getComment());

        try {
            Statement statement = Database.getConnection().createStatement();
            int rows = statement.executeUpdate(createReview);
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create review");
        }

        return false;
    }
}
