package sample;

import Domaine.mapper.UserCompte;
import dao.service.SignUpImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField nametxt;
    @FXML
    private TextField prenomtxt;
    @FXML
    private TextField emailtxt;
    @FXML
    private PasswordField passwordtxt;
    @FXML
    private TextField codeutxt;

    public void getLogin(ActionEvent event){
        String name =  nametxt.getText();
        String prenom = prenomtxt.getText();
        String email = emailtxt.getText();
        String password = passwordtxt.getText();
        String params = email+ "   " + password;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText(params);
        alert.showAndWait();
    }

    public void SignUp(ActionEvent event){

        String name =  nametxt.getText();
        String prenom = prenomtxt.getText();
        String email = emailtxt.getText();
        String password = passwordtxt.getText();
        String codeu = codeutxt.getText();
        String params = name+ "   " + prenom+ "   " + email+ "   " + password+ "  "+ codeu;

        UserCompte userCompte = new UserCompte(name, prenom, email, password,codeu);

        SignUpImpl signUp = new SignUpImpl();


        int resultat = signUp.SignUp(userCompte);

        System.out.println(resultat);

    }


}
