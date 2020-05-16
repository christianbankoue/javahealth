package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {


    public TextField nomMedecin;
    public TextField prenomMedecin;

    public TextField nomPatient;
    public TextField prenomPatient;

    public TextField maladie;
    public TextArea description;



    Utilisateur medecinUser;
    Utilisateur patient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void injectUtilisateur(Utilisateur medecinUser, Utilisateur patient) {
        this.medecinUser = medecinUser;
        this.patient = patient;

        nomPatient.setText(this.patient.getName());
        prenomPatient.setText(this.patient.getPrenom());

        nomMedecin.setText(this.medecinUser.getName());
        prenomMedecin.setText(this.medecinUser.getPrenom());

    }
}
