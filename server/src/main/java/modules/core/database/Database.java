package modules.core.database;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/IvanDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("JDBC Driver doesn't exists");
            }
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed!");
        }

        return connection;
    }
}
