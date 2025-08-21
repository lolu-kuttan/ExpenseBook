package expense_tracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminLoginFormController {

    @FXML private TextField adminUsernameField;
    @FXML private PasswordField adminPasswordField;
    @FXML private Label statusLabel;

    @FXML
    public void handleAdminLogin(ActionEvent event) {
        String username = adminUsernameField.getText().trim();
        String password = adminPasswordField.getText().trim();

        // You can change this to a DB lookup if needed later
        if (username.equals("admin") && password.equals("admin123")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserList.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setTitle("Registered Users");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                statusLabel.setText("Failed to load expense list.");
            }
        } else {
            statusLabel.setText("Invalid admin credentials.");
        }
    }
    @FXML
    private void handleBackToRoleSelection(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogin.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Select Login Type");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

