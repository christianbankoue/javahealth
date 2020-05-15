package controller;

import dao.service.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Programmation;
import model.Utilisateur;
import model.enums.DomaineMedical;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProgrammationController  implements Initializable {

    Utilisateur utilisateur;

    @FXML
    private TextField nametxt;
    @FXML
    private TextField prenomtxt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nametxt.setText("...");
        prenomtxt.setText("...");
    }

    public void injectUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
        nametxt.setText(utilisateur.getName());
        prenomtxt.setText(utilisateur.getPrenom());
    }

    public void valider(ActionEvent event) {

        Programmation programmation = new Programmation();

        programmation.setCodePatient(utilisateur.getCodeUnique());
        programmation.setNamePatient(utilisateur.getName());
        programmation.setPrenomPatient(utilisateur.getPrenom());

        //--//
        programmation.setDomaineMedical(DomaineMedical.CONTROLE_DE_ROUTINE.name());
        programmation.setDate( LocalDate.now());

        //affichage des 2 du bas a l action du select sur les 2 du haut
        //on va simuller une recherche des hospitaux et medecin
        programmation.setHospital("Hospital 1");
        programmation.setMedecinFullName("Jacques eric");


        UserServiceImp serviceImp = new UserServiceImp();
        serviceImp.addProgrammation(programmation);

        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

    }
}
