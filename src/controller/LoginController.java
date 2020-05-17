package controller;

import dao.service.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.UserCompte;
import model.Utilisateur;
import model.enums.PersonnelMedicalEnum;
import model.enums.RoleEnum;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.*;

public class LoginController implements Initializable {


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
    @FXML
    private TextField role;
    @FXML
    private Label erreur;

    private RoleEnum roleEnum = RoleEnum.INEXISTANT;
    private PersonnelMedicalEnum personnelMedical = PersonnelMedicalEnum.MEDECIN;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    erreur.setVisible(false);
    }

    @FXML
    private void medecin(ActionEvent event){
        role.setText("medecin");
        roleEnum = RoleEnum.PERSONNEL_MEDICAL;
        personnelMedical = PersonnelMedicalEnum.MEDECIN;
    }

    @FXML
    private void assistant(ActionEvent event){
        role.setText("Assistant");
        roleEnum = RoleEnum.PERSONNEL_MEDICAL;
        personnelMedical = PersonnelMedicalEnum.ASSISTANT;
    }

    @FXML
    private void infirmier(ActionEvent event){
        role.setText("Infirmier");
        roleEnum= RoleEnum.PERSONNEL_MEDICAL;
        personnelMedical = PersonnelMedicalEnum.INFIRMIERE;
    }

    @FXML
    private void patient(ActionEvent event){
        role.setText("Patient");
        roleEnum = RoleEnum.PATIENT;
    }

    @FXML
    private void pharmacien(ActionEvent event){
        role.setText("Pharamcien");
        roleEnum = RoleEnum.PHARMACIEN;
    }

    @FXML
    private void fournisseur(ActionEvent event){
        role.setText("Fournisseur");
        roleEnum = RoleEnum.FOURNISSEUR;
    }

    public void login(ActionEvent event){
        String name =  nametxt.getText();
        String prenom = prenomtxt.getText();
        String email = emailtxt.getText();
        String password = passwordtxt.getText();

        UserServiceImp userService = new UserServiceImp();

        Utilisateur utilisateur = userService.login(name, prenom, email, password);

        System.out.println(utilisateur);

        if(utilisateur.getCodeUnique() != null){
            try {
                ((Node)event.getSource()).getScene().getWindow().hide();

                Stage gestionconsultaionStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("./../vue/gestionconsultaion.fxml").openStream());

                GestionConsultaionController gcc =  (GestionConsultaionController)loader.getController();
                gcc.injectUtilisateur(utilisateur);

                Scene scene = new Scene(root);
                gestionconsultaionStage.setScene(scene);
                gestionconsultaionStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            // label erreur login
            // isUserConnected.setText("Erreur login verifier vos informations");
            erreur.setVisible(true);
        }

    }

    public void signUp(ActionEvent event){

        String name =  nametxt.getText();
        String prenom = prenomtxt.getText();
        String email = emailtxt.getText();
        String password = passwordtxt.getText();
        String codeUnique = codeutxt.getText();


        if(name.length()>0 && prenom.length()>0 && email.length()>0 && password.length() >0 && codeUnique.length()>0 && !roleEnum.name().equals(RoleEnum.INEXISTANT.name())) {
            UserCompte userCompte = new UserCompte(name, prenom, email, password, codeUnique);

            if (roleEnum.getIdentifiant() == 1)
                userCompte.setAdmin(true);
            else if (roleEnum.getIdentifiant() == 2) {
                userCompte.setPersonnelmedicale(true);
                if (personnelMedical.name().equals(PersonnelMedicalEnum.ASSISTANT.name())) {
                    userCompte.setTypePersonnelMedical("ASSISTANT");
                } else if (personnelMedical.name().equals(PersonnelMedicalEnum.MEDECIN.name())) {
                    userCompte.setTypePersonnelMedical("MEDECIN");
                } else if (personnelMedical.name().equals(PersonnelMedicalEnum.INFIRMIERE.name())) {
                    userCompte.setTypePersonnelMedical("INFIRMIERE");
                }
            } else if (roleEnum.getIdentifiant() == 3)
                userCompte.setFournisseur(true);
            else if (roleEnum.getIdentifiant() == 4)
                userCompte.setPharmatien(true);
            else if (roleEnum.getIdentifiant() == 5)
                userCompte.setPatient(true);


            UserServiceImp userService = new UserServiceImp();

            int resultat = userService.signUp(userCompte);

            System.out.println(resultat);

            Utilisateur utilisateur = userService.login(name, prenom, email, password);

            System.out.println(utilisateur);

            if (resultat > 0) {
                try {
                    ((Node) event.getSource()).getScene().getWindow().hide();

                    Stage gestionconsultaionStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = loader.load(getClass().getResource("./../vue/gestionconsultaion.fxml").openStream());

                    GestionConsultaionController gcc = (GestionConsultaionController) loader.getController();
                    gcc.injectUtilisateur(utilisateur);

                    Scene scene = new Scene(root);
                    gestionconsultaionStage.setScene(scene);
                    gestionconsultaionStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // label erreur signUp
                // isUserConnected.setText("Erreur signUp verifier vos informations");
                erreur.setVisible(true);
            }
        }else {
            erreur.setVisible(true);
        }
    }

    public void signIn(ActionEvent event){
        String name =  nametxt.getText();
        String prenom = prenomtxt.getText();
        String email = emailtxt.getText();
        String password = passwordtxt.getText();
        if(name.length()>0 && prenom.length()>0 && email.length()>0 && password.length() >0 ) {

            UserServiceImp userService = new UserServiceImp();

            Utilisateur utilisateur = userService.login(name, prenom, email, password);

            System.out.println(utilisateur);

            if (utilisateur.getCodeUnique() != null) {
                try {
                    ((Node) event.getSource()).getScene().getWindow().hide();

                    Stage gestionconsultaionStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = loader.load(getClass().getResource("./../vue/gestionconsultaion.fxml").openStream());

                    GestionConsultaionController gcc = (GestionConsultaionController) loader.getController();
                    gcc.injectUtilisateur(utilisateur);

                    Scene scene = new Scene(root);
                    gestionconsultaionStage.setScene(scene);
                    gestionconsultaionStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // label erreur login
                // isUserConnected.setText("Erreur login verifier vos informations");
                erreur.setVisible(true);
            }
        }else {
            erreur.setVisible(true);
        }

    }

    public void sigIn(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("./../vue/login.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public void connect(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("./../vue/signIn.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
