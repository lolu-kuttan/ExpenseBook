package expense_tracker;
import expense_tracker.DatabaseHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;

public class UserLoginFormController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill in both fields.");
            return;
        }
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

         try(Connection conn = DatabaseHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
           try (ResultSet rs = pstmt.executeQuery()) {


            if (rs.next()) {
                // Save logged-in username
                LoggedInUser.setUsername(username);

                // Go to ExpenseForm
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpenseForm.fxml"));
                Parent root = loader.load();

// Pass username to the controller
                // You need to implement this method

                Scene scene = new Scene(root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Expense Form - User");
                stage.show();

            } else {
                statusLabel.setText("Incorrect username or password.");
            }}

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Login error.");
        }
    }
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogin.fxml")); // Role selection page
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Select Role");
        stage.show();
    }
}

