package expense_tracker;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ExpenseFormController {

    @FXML
    private TextField dateField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField amountField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label statusLabel;

    @FXML
    private void handleAddExpense() {
        String date = dateField.getText().trim();
        String category = categoryField.getText().trim();
        String amountText = amountField.getText().trim();
        String description = descriptionField.getText().trim();

        if (date.isEmpty() || category.isEmpty() || amountText.isEmpty()) {
            statusLabel.setText("Please fill in all required fields.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            boolean success = DatabaseHelper.insertExpense(date, category, amount, description);

            if (success) {
                statusLabel.setText("Expense added successfully.");
                clearForm();
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> statusLabel.setText(""));
                pause.play();
            } else {
                statusLabel.setText("Failed to add expense.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Amount must be a valid number.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void clearForm() {
        dateField.clear();
        categoryField.clear();
        amountField.clear();
        descriptionField.clear();
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void handleViewExpenses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpenseList.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("All Expenses");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleBackToUserLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

