package expense_tracker;

import java.sql.*;

public class DatabaseHelper
{
    private static final String DB_URL = "jdbc:sqlite:C:/Users/asus/OneDrive/Desktop/IdeaProjects/JavaFx/Database/expenses.db";
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite established.");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }


    // Create expenses table
    public static void createTable()
    {

        String sql = "CREATE TABLE IF NOT EXISTS expenses ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT,"
                + "date TEXT NOT NULL,"
                + "category TEXT NOT NULL,"
                + "amount REAL NOT NULL,"
                + "description TEXT"
                + ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Expenses table is ready.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }
    public static boolean insertExpense(String date, String category, double amount, String description) {
        String sql = "INSERT INTO expenses(date, category, amount, description) VALUES(?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date);
            pstmt.setString(2, category);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, description);

            int rowsInserted = pstmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }



}
