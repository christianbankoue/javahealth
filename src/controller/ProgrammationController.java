package controller;

import dao.service.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Programmation;
import model.Utilisateur;
import model.enums.DomaineMedical;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class ProgrammationController implements Initializable {



    Utilisateur utilisateur;


    @FXML
    private TextField nametxt;
    @FXML
    private TextField prenomtxt;
    @FXML
    private TextField hopitalSelected;
    @FXML
    private TextField domaineMedicalselected;
    @FXML
    private TextField medecinSelected;
    @FXML
    public Button valideB;
    @FXML
    public DatePicker datePickerS;
    @FXML
    public SplitMenuButton medecinList;
    @FXML
    public SplitMenuButton hopitalList;
    @FXML
    public SplitMenuButton domaineList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nametxt.setText("...");
        prenomtxt.setText("...");

        UserServiceImp serviceImp = new UserServiceImp();
        List<Utilisateur> medecin = serviceImp.getUtilisateurByTyPeMedical("MEDECIN");
        medecin.forEach(utilisateur -> {
            MenuItem choice = new MenuItem(utilisateur.getName() +" - "+ utilisateur.getPrenom());
            choice.setOnAction((e)-> {
                medecinSelected.setText(choice.getText());
            });
            medecinList.getItems().add(choice);
        });

    }

    public void hopitalun(ActionEvent event){
        hopitalSelected.setText("hopital 1");
    }

    public void hopitaldeux(ActionEvent event){
        hopitalSelected.setText("hopital 2");
    }

    public void domaineMedical(ActionEvent event){
        MenuItem mn = (MenuItem)event.getSource();
        domaineMedicalselected.setText(mn.getText());
    }

    public void injectUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
        nametxt.setText(utilisateur.getName());
        nametxt.setDisable(true);
        prenomtxt.setText(utilisateur.getPrenom());
        prenomtxt.setDisable(true);
    }

    public void injectProgrammation(String name, String prenom, String domaineMedical, String medecinFullName, String hospital, LocalDateTime date){
        nametxt.setText(name);
        nametxt.setDisable(true);
        prenomtxt.setText(prenom);
        prenomtxt.setDisable(true);
        domaineMedicalselected.setText(domaineMedical);
        hopitalSelected.setText(hospital);
        medecinSelected.setText(medecinFullName);
        medecinList.setDisable(true);
        domaineList.setDisable(true);
        hopitalList.setDisable(true);
        // datePickerS.setValue(date); TODO
        datePickerS.setDisable(true);
        valideB.setText("OK");
    }

    public void valider(ActionEvent event) {

        if(!valideB.getText().equals("OK")){
            Programmation programmation = new Programmation();

            programmation.setCodePatient(utilisateur.getCodeUnique());
            programmation.setNamePatient(utilisateur.getName());
            programmation.setPrenomPatient(utilisateur.getPrenom());

            //--//
            programmation.setDomaineMedical(DomaineMedical.valueOf(domaineMedicalselected.getText()).name());

            LocalDate value = datePickerS.getValue();
            programmation.setDate(value.atStartOfDay());

            //affichage des 2 du bas a l action du select sur les 2 du haut
            //on va simuller une recherche des hospitaux et medecin
            programmation.setHospital(hopitalSelected.getText());
            programmation.setMedecinFullName(medecinSelected.getText());


            UserServiceImp serviceImp = new UserServiceImp();
            serviceImp.addProgrammation(programmation);
        }

        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

    }


}
