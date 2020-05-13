package controller;

import javafx.fxml.Initializable;
import model.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionConsultaionController implements Initializable {

    Utilisateur utilisateur;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void injectUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }
}
