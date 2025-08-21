package expense_tracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class UserListController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadUsers();
    }

    private void loadUsers() {
        String query = "SELECT username, password FROM users";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/asus/OneDrive/Desktop/IdeaProjects/JavaFx/Database/expenses.db");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                userList.add(new User(rs.getString("username"), rs.getString("password")));
            }

            userTable.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml")); // Role selection page
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Select Role");
        stage.show();
    }

}

