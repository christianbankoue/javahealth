package controller;

import dao.service.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Consultation;
import model.Programmation;
import model.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ResourceBundle;

public class GestionConsultaionController implements Initializable {


    Utilisateur utilisateur;

    @FXML
    Button logout;

    @FXML
    private ListView<String> listViewPg;

    @FXML
    public ListView<String> listViewCs;

    @FXML
    public Button consultationKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("lll");
    }

    public void injectUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;

        if(utilisateur.getRole().getIdentifiant() == 2 && "MEDECIN".equals(utilisateur.getPersonnelMedical().name())){
            consultationKey.setDisable(false);
            getAllProgrammations();
            getAllConsultations();
        }else{
            consultationKey.setDisable(true);
            getAllProgrammations();
        }
    }

    public void programmation(ActionEvent event){
        detailProgrammation();
        getAllProgrammations();
    }

    private void getAllProgrammations() {
        UserServiceImp serviceImp = new UserServiceImp();
        if(this.utilisateur != null){

            List<Programmation> pgs = serviceImp.
                    getAllProgrammation(this.utilisateur.getCodeUnique(), this.utilisateur.getName(), this.utilisateur.getPrenom());

            listViewPg.getItems().remove(0, listViewPg.getItems().size());

            pgs.forEach(programmation -> {
                String date = programmation.getDate().getDayOfMonth() + "/"+
                        programmation.getDate().getMonth().toString() +"/"+
                        programmation.getDate().getYear();

                Long epoque = (programmation.getDate().atStartOfDay().toInstant(ZoneOffset.UTC)).getEpochSecond();
                String key = String.valueOf(epoque +""+ programmation.getProgrammation_id() +""+ String.valueOf(epoque).hashCode());
                listViewPg.getItems().add(
                        key +" # "+ programmation.getNamePatient() + " # " +date);
            });

        }
    }

    public void consultation(ActionEvent actionEvent) {

        boolean success = detailConsultatation();
        if(success){
            getAllConsultations();
        }

    }

    private void getAllConsultations() {
        UserServiceImp serviceImp = new UserServiceImp();
        List<Consultation> consultations = serviceImp.getConsultationByCodeUnique(this.utilisateur.getCodeUnique());

        listViewCs.getItems().remove(0, listViewCs.getItems().size());

        consultations.forEach(consultation -> {
            String date = consultation.getDateVisite().getDayOfMonth() + "/"+
                    consultation.getDateVisite().getMonth().toString() +"/"+
                    consultation.getDateVisite().getYear();

            Long epoque = (consultation.getDateVisite().atStartOfDay().toInstant(ZoneOffset.UTC)).getEpochSecond();
            String key = String.valueOf(epoque +""+ consultation.getConsultation_id() +""+ String.valueOf(epoque).hashCode());

            listViewCs.getItems().add(
                    key +" # "+ consultation.getNamePatient() +" # "+ consultation.getNameMedecin() + " # " +date);
        });

    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        System.out.println("clicked on " + listViewPg.getSelectionModel().getSelectedItem());
        //158950080011374326664 - Merlin - 15/MAY/2020

        String selectedItem = listViewPg.getSelectionModel().getSelectedItem();
        if(selectedItem != null ){
            String[] donnees = selectedItem.split("#");
            String key = donnees[0].trim();
            String namePatient = donnees[1].trim();
            String date = donnees[2].trim();

            String[] dateSplited = date.split("/");
            String day = dateSplited[0];
            String month = dateSplited[1];
            String year = dateSplited[2];
            LocalDate l = LocalDate.of(Integer.parseInt(year), Month.valueOf(month), Integer.parseInt(day));

            Long epoque = (l.atStartOfDay().toInstant(ZoneOffset.UTC)).getEpochSecond();

            String[] keySplited = key.split(String.valueOf(epoque));
            int programmation_id = Integer.parseInt(keySplited[1].substring(0,1));

            UserServiceImp serviceImp = new UserServiceImp();
            Programmation programmation = serviceImp.getProgrammation(programmation_id);

            try {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Detail de votre programmation "+key);

                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("./../vue/programmation.fxml").openStream());

                ProgrammationController pc = loader.getController();

                pc.injectProgrammation( utilisateur.getName(),  utilisateur.getPrenom(),
                        programmation.getDomaineMedical(), programmation.getMedecinFullName(), programmation.getHospital(), programmation.getDate());

                Scene scene = new Scene(root);
                popupwindow.setScene(scene);
                popupwindow.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public void handleMouseClickCs(MouseEvent mouseEvent) {
        System.out.println("clicked on " + listViewCs.getSelectionModel().getSelectedItem());
        //158950080011374326664 - Merlin - 15/MAY/2020

        String selectedItem = listViewCs.getSelectionModel().getSelectedItem();
        if(selectedItem != null ){

            String[] donnees = selectedItem.split("#");

            String key = donnees[0].trim();
            String namePatient = donnees[1].trim();
            String nameMedecin = donnees[2].trim();
            String date = donnees[3].trim();

            String[] dateSplited = date.split("/");
            String day = dateSplited[0];
            String month = dateSplited[1];
            String year = dateSplited[2];
            LocalDate l = LocalDate.of(Integer.parseInt(year), Month.valueOf(month), Integer.parseInt(day));

            Long epoque = (l.atStartOfDay().toInstant(ZoneOffset.UTC)).getEpochSecond();

            String[] keySplited = key.split(String.valueOf(epoque));
            int consultation_id = Integer.parseInt(keySplited[1].substring(0,1));

            UserServiceImp serviceImp = new UserServiceImp();
            Consultation consultation = serviceImp.getConsultationById(consultation_id);

            try {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Detail de votre consultation "+key);

                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("./../vue/consultation.fxml").openStream());

                ConsultationController pc = loader.getController();

                pc.injectUtilisateur(utilisateur, consultation);

                Scene scene = new Scene(root);
                popupwindow.setScene(scene);
                popupwindow.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void detailProgrammation(){

        try {
            Stage popupwindow = new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Nouvelle programmation");

            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("./../vue/programmation.fxml").openStream());

            ProgrammationController pc = loader.getController();
            pc.injectUtilisateur(utilisateur);

            Scene scene = new Scene(root);
            popupwindow.setScene(scene);
            popupwindow.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean detailConsultatation(){

        try {
            //Si on a des programmations liees a ce medecin alors il peut faire des consultations

            String medecinFullName = utilisateur.getName()+ " - " +utilisateur.getPrenom();
            UserServiceImp serviceImp = new UserServiceImp();
            List<Programmation> programmations = serviceImp.getAllProgrammationByMedecin(medecinFullName);

            if(programmations.size() > 0 ){

                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Nouvelle Consultation");

                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("./../vue/consultation.fxml").openStream());

                ConsultationController pc = loader.getController();

                pc.injectUtilisateur(utilisateur, programmations);

                Scene scene = new Scene(root);
                popupwindow.setScene(scene);
                popupwindow.showAndWait();

                return true;
            }else{
                // TODO Ajouter un pop up(Alert)
                System.out.println("Vous n avez pas de consultation en cours...");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
