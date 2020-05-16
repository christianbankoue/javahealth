package controller;

import dao.service.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {


    public TextField nomMedecin;
    public TextField prenomMedecin;

    public TextField nomPatient;
    public TextField prenomPatient;

    public TextField maladieLabel;
    public TextArea description;
    public Button validerButton;

    @FXML
    private ListView<String> listViewPg;

    Utilisateur medecinUser;
    List<Programmation> pgs;
    String codePatient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomMedecin.setDisable(true);
        prenomMedecin.setDisable(true);
        nomPatient.setDisable(true);
        prenomPatient.setDisable(true);
        validerButton.setDisable(true);
    }

    public void injectUtilisateur(Utilisateur medecinUser, Consultation consultation) {
        this.medecinUser = medecinUser;

        listViewPg.getItems().remove(0, listViewPg.getItems().size());
        listViewPg.setDisable(true);

        nomMedecin.setText(this.medecinUser.getName());
        prenomMedecin.setText(this.medecinUser.getPrenom());

        nomPatient.setText(consultation.getNamePatient());
        prenomPatient.setText(consultation.getPrenomPatient());


        UserServiceImp serviceImp = new UserServiceImp();
        Maladie maladie = serviceImp.getMaladieByConsultationId(consultation.getConsultation_id());
        description.setText(maladie.getDescription());
        maladieLabel.setText(maladie.getNom());

        codePatient = consultation.getCodeUniquePatient();
        validerButton.setDisable(false);
        validerButton.setText("OK");
    }

    public void injectUtilisateur(Utilisateur medecinUser, List<Programmation> pgs) {
        this.medecinUser = medecinUser;
        this.pgs = pgs;

        listViewPg.getItems().remove(0, listViewPg.getItems().size());

        pgs.forEach(programmation -> {
            String date = programmation.getDate().getDayOfMonth() + "/" +
                    programmation.getDate().getMonth().toString() + "/" +
                    programmation.getDate().getYear();

            Long epoque = (programmation.getDate().toLocalDate().atStartOfDay().toInstant(ZoneOffset.UTC)).getEpochSecond();
            String key = String.valueOf(epoque + "" + programmation.getProgrammation_id() + "" + String.valueOf(epoque).hashCode());
            listViewPg.getItems().add(
                    key + " # " + programmation.getNamePatient() + " # " + date);
        });

        nomMedecin.setText(this.medecinUser.getName());
        prenomMedecin.setText(this.medecinUser.getPrenom());
        validerButton.setText("Selectionner une programmation");
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        validerButton.setText("VALIDER");
        validerButton.setDisable(false);
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
        int programmation_id = Integer.parseInt(keySplited[1].substring(0, 1));

        UserServiceImp serviceImp = new UserServiceImp();
        Programmation programmation = serviceImp.getProgrammation(programmation_id);

        nomPatient.setText(programmation.getNamePatient());
        prenomPatient.setText(programmation.getPrenomPatient());
        codePatient = programmation.getCodePatient();

    }

    public void valider(ActionEvent actionEvent) {

        if (validerButton.getText().equals("VALIDER")) {

            //on valide une consultation seulement si la maladie est indiquee
            if (maladieLabel.getText() != null && maladieLabel.getText().length() > 0
                    && nomPatient.getText() != null && nomPatient.getText().length() > 0
                    && prenomPatient.getText() != null && prenomPatient.getText().length() > 0) {

                Consultation cst = new Consultation(codePatient, nomPatient.getText(), prenomPatient.getText(),
                        medecinUser.getCodeUnique(), prenomMedecin.getText(), prenomMedecin.getText());
                cst.setDateVisite(LocalDateTime.now());

                //ajout de la consultation
                UserServiceImp serviceImp = new UserServiceImp();
                int result = serviceImp.addConsultation(cst);

                //on recupere la dite consultation de la base de donnees
                java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(cst.getDateVisite());
                Consultation cstResult = serviceImp.getCstByCodeUAndDate(cst.getCodeUniquePatient(),
                        cst.getCodeUniqueMedecin(), sqlDate);

                System.out.println(cstResult);

                //On sauvegarde la recette
                Recette recette = new Recette("label1", "detail1", medecinUser.getId(), 1, LocalDateTime.now());
                serviceImp.addRecette(recette);

                Recette recetteSaved = serviceImp.getRecetteByMedecinAndDateAndPharnacien(medecinUser.getId(), 1, java.sql.Timestamp.valueOf(recette.getDate()));

                //On sauvegarde la maladie detectee lors de la consultation
                Maladie maladie = new Maladie(maladieLabel.getText(), description.getText(),
                        cstResult.getConsultation_id(), recetteSaved.getRecette_id());
                serviceImp.addMaladie(maladie);
            }


        }

        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();

    }

}
