package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField emailtxt;
    @FXML
    private PasswordField passwordtxt;

    public void getLogin(ActionEvent event){
        String email = emailtxt.getText();
        String password = passwordtxt.getText();
        String params = email+ "   " + password;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText(params);
        alert.showAndWait();
    }
}
