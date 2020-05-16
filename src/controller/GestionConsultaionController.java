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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void injectUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
        getAllProgrammations();
    }

    public void programmation(ActionEvent event){
        detailProgrammation(true);
        getAllProgrammations();
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        System.out.println("clicked on " + listViewPg.getSelectionModel().getSelectedItem());
        //158950080011374326664 - Merlin - 15/MAY/2020

        String[] donnees = listViewPg.getSelectionModel().getSelectedItem().split("#");

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
            popupwindow.setTitle("Nouvelle programmation");

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

    public void detailProgrammation(Boolean edit){

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

    private void getAllProgrammations() {
        UserServiceImp serviceImp = new UserServiceImp();
        if(this.utilisateur != null /*&& utilisateur.getRole().getIdentifiant() == 5*/){

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
}
