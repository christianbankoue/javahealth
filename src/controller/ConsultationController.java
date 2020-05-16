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
import model.Consultation;
import model.Programmation;
import model.Utilisateur;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {

    public Button valider;


    public TextField nomMedecin;
    public TextField prenomMedecin;

    public TextField nomPatient;
    public TextField prenomPatient;

    public TextField maladie;
    public TextArea description;

    @FXML
    private ListView<String> listViewPg;

    Utilisateur medecinUser;
    List<Programmation> pgs;
    String codePatient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void injectUtilisateur(Utilisateur medecinUser, List<Programmation> pgs) {
        this.medecinUser = medecinUser;
        this.pgs = pgs;

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

        nomMedecin.setText(this.medecinUser.getName());
        prenomMedecin.setText(this.medecinUser.getPrenom());
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

        nomPatient.setText(programmation.getNamePatient());
        prenomPatient.setText(programmation.getPrenomPatient());
        codePatient = programmation.getCodePatient();

    }

    public void valider(ActionEvent actionEvent) {

        if(valider.getText().equals("VALIDERkkkk")){
            Consultation consultation = new Consultation(codePatient, nomPatient.getText(), prenomPatient.getText(),
                    medecinUser.getCodeUnique(), prenomMedecin.getText(), prenomMedecin.getText());
            consultation.setDateVisite(LocalDate.now());

            UserServiceImp serviceImp = new UserServiceImp();
            int result = serviceImp.addConsultation(consultation);

            java.sql.Date sqlDate = java.sql.Date.valueOf(consultation.getDateVisite());
            Consultation consultationByCodeUniqueAndDate = serviceImp.getConsultationByCodeUniqueAndDate(consultation.getCodeUniquePatient(), consultation.getCodeUniqueMedecin(), sqlDate);
            System.out.println(result);

        }

        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();

    }
}
