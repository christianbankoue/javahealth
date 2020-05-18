package controller;

import dao.service.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionConsultaionController implements Initializable {


    public Label listproduitlabel;
    public Label listeuserlabel;
    Utilisateur utilisateur;

    @FXML
    Button logout;

    @FXML
    private ListView<String> listViewPg;

    @FXML
    public Label listViewPgLabel;

    @FXML
    public ListView<String> listViewCs;

    @FXML
    public Label listCsLabel;

    @FXML
    public ListView<String> listRecette;

    @FXML
    public VBox vbRecette;

    @FXML
    public ListView<String> listProduit;

    @FXML
    public CheckBox produitCB;

    @FXML
    public ListView<String> listUtilisateur;

    @FXML
    public CheckBox utilisateurCB;

    @FXML
    public Button consultationKey;

    /*ChangeListener produitCBChange = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {
                getAllProduit();
        }
    };
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilisateurCB.selectedProperty().addListener((ov, old_val, new_val) -> getAllUser());
        produitCB.selectedProperty().addListener((ov, old_val, new_val) -> getAllProduit());
    }

    public void injectUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;

        //si l utilisateur est admin
        if(utilisateur.getRole().getIdentifiant() == 1){
            consultationKey.setDisable(true);
            listViewCs.setVisible(false);
            listCsLabel.setVisible(false);

            System.out.println("L'utilisateur est un admin");
            getAllUser();
            getAllProduit();
        }
        //si l utilisateur est fournisseur
        else if(utilisateur.getRole().getIdentifiant() == 3){
            consultationKey.setDisable(false);
            listViewCs.setVisible(false);
            listCsLabel.setVisible(false);
            listRecette.setVisible(false);
            listeuserlabel.setVisible(false);
            listUtilisateur.setVisible(false);
            utilisateurCB.setVisible(false);
            vbRecette.setVisible(false);


            System.out.println("L'utilisateur est un fournisseur");
        }
        //si l utilisateur est un pharmacien
        else if(utilisateur.getRole().getIdentifiant() == 4){
            consultationKey.setDisable(false);
            listViewCs.setVisible(false);
            listCsLabel.setVisible(false);
            listUtilisateur.setVisible(false);
            listProduit.setVisible(false);
            listproduitlabel.setVisible(false);
            listeuserlabel.setVisible(false);
            utilisateurCB.setVisible(false);
            produitCB.setVisible(false);
            vbRecette.setVisible(false);

            getAllProgrammations();
            getAllRecettes();
        }
        //si l utilisateur est un PMEDICAL de type MEDECIN ou ASSISTANT
        else if(utilisateur.getRole().getIdentifiant() == 2 &&
            ("MEDECIN".equals(utilisateur.getPersonnelMedical().name())
            || "ASSISTANT".equals(utilisateur.getPersonnelMedical().name()))){

            consultationKey.setDisable(false);
            listUtilisateur.setVisible(false);
            listProduit.setVisible(false);
            listproduitlabel.setVisible(false);
            listeuserlabel.setVisible(false);
            listRecette.setVisible(false);
            vbRecette.setVisible(false);
            utilisateurCB.setVisible(false);
            produitCB.setVisible(false);
            getAllProgrammations();
            getAllConsultations();
        }else{
            //si l utilisateur est un autre type (Patient)
            consultationKey.setDisable(false);
            listViewCs.setVisible(false);
            listCsLabel.setVisible(false);
            listProduit.setVisible(false);
            listproduitlabel.setVisible(false);
            listeuserlabel.setVisible(false);
            listRecette.setVisible(false);
            vbRecette.setVisible(false);
            listUtilisateur.setVisible(false);
            utilisateurCB.setVisible(false);
            produitCB.setVisible(false);
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

                Long epoque = (programmation.getDate().toLocalDate().atStartOfDay().toInstant(ZoneOffset.UTC)).getEpochSecond();
                String epoqueStr = epoque.toString().substring(0, 7);
                String key = epoqueStr +""+ programmation.getProgrammation_id() +"@@"+ String.valueOf(epoque).hashCode();
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
            String epoqueStr = epoque.toString().substring(0, 7);

            String key = epoqueStr +""+ consultation.getConsultation_id() +"@@"+ String.valueOf(epoque).hashCode();

            listViewCs.getItems().add(
                    key +" # "+ consultation.getNamePatient() +" # "+ consultation.getNameMedecin() + " # " +date);
        });

    }

    private void getAllRecettes(){

        UserServiceImp serviceImp = new UserServiceImp();
        List<Recette> recettes = serviceImp.getRecettesByPharmacien(this.utilisateur.getId());

        listRecette.getItems().remove(0, listRecette.getItems().size());

        recettes.stream()
                .filter(recette ->  recette.getMedicamentDelivrer() != 1 )
                .forEach(recette -> {
                    Utilisateur medecin = serviceImp.getUtilisateurById(recette.getMedecin_id());
                    listRecette.getItems().add(
                            recette.getLabel() + " # " + recette.getRecette_id()+ " # prescrit par > "+ medecin.getName() );
                });

    }

    private void getAllUser(){

        UserServiceImp serviceImp = new UserServiceImp();
        List<Utilisateur> utilisateurs = serviceImp.getAllUser();
        //List<Utilisateur> utilisateurs = serviceImp.getUtilisateurByRoleId(5);

        listUtilisateur.getItems().remove(0, listUtilisateur.getItems().size());

        if(utilisateurCB.isSelected()){
            utilisateurs.stream()
                .filter(utilisateur -> utilisateur.getInactive() == 1)
                .forEach( utilisateur -> {
                    // on regarde si le produit a toutes les info
                    listUtilisateur.getItems().add( utilisateur.getId() + " - "+ utilisateur.getPrenom() + " - "+ utilisateur.getName() );
                });
        }else{
            utilisateurs.forEach( utilisateur -> {
                listUtilisateur.getItems().add( utilisateur.getId() + " - "+ utilisateur.getPrenom() + " - "+ utilisateur.getName() );
                });
        }


    }

    private void getAllProduit(){

        UserServiceImp serviceImp = new UserServiceImp();
        List<Produit> produits = serviceImp.getAllProduit();

        listProduit.getItems().remove(0, listProduit.getItems().size());

        if(produitCB.isSelected()){
            produits.stream()
                .filter(produit -> !(produit.getLabel() != null && produit.getLabel().length() > 0 &&
                                    produit.getDetail() != null && produit.getDetail().length() > 0))
                .forEach( produit -> {
                    // on regarde si le produit a toutes les info
                    listProduit.getItems().add( produit.getProduit_id() + " - "+ produit.getLabel());
                });
        }else{
            produits.forEach( produit -> {
                    listProduit.getItems().add( produit.getProduit_id() + " - "+ produit.getLabel());
                });
        }

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
            String epoqueStr = epoque.toString().substring(0, 7);

            String[] keySplited = key.split(epoqueStr);
            int programmation_id = Integer.parseInt(keySplited[1].split("@@")[0]);

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
            String epoqueStr = epoque.toString().substring(0, 7);

            String[] keySplited = key.split(epoqueStr);
            int consultation_id = Integer.parseInt(keySplited[1].split("@@")[0]);

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

    public void handleMouseClickRc(MouseEvent mouseEvent) {
        System.out.println("clicked on " + listRecette.getSelectionModel().getSelectedItem());

        String selectedItem = listRecette.getSelectionModel().getSelectedItem();
        if(selectedItem != null ){
            String[] donnees = selectedItem.split("#");
            String label = donnees[0].trim();
            String id = donnees[1].trim();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("La recette "+label+" va etre donne au patient");
            alert.setContentText("Vous le confirmez?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                // ... user chose OK
                alert.close();
                UserServiceImp serviceImp = new UserServiceImp();
                serviceImp.updateRecettesById(Integer.parseInt(id));

                getAllRecettes();
            } else {
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
        }

    }

    public void handleMouseClickUtilisateur(MouseEvent mouseEvent) {
        System.out.println("clicked on " + listUtilisateur.getSelectionModel().getSelectedItem());

        String selectedItem = listUtilisateur.getSelectionModel().getSelectedItem();
        if(selectedItem != null && utilisateurCB.isSelected()){
            String[] donnees = selectedItem.split("-");
            String id = donnees[0].trim();
            String prenom = donnees[1].trim();
            String nom = donnees[2].trim();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("L utilisateur"+ prenom +" "+ nom +" va etre suprimer");
            alert.setContentText("Vous le confirmez?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                // ... user chose OK
                alert.close();
                UserServiceImp serviceImp = new UserServiceImp();
                serviceImp.deleteUtilisateur(Integer.parseInt(id));
                utilisateurCB.setSelected(false);
                getAllUser();
            } else {
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
        }

    }

    public void handleMouseClickProduit(MouseEvent mouseEvent) {
        System.out.println("clicked on " + listProduit.getSelectionModel().getSelectedItem());

        String selectedItem = listProduit.getSelectionModel().getSelectedItem();
        if(selectedItem != null && produitCB.isSelected()){
            String[] donnees = selectedItem.split("-");
            String id = donnees[0].trim();
            String label = donnees[1].trim();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Le produit "+label+" va etre suprimer");
            alert.setContentText("Vous le confirmez?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                // ... user chose OK
                alert.close();
                UserServiceImp serviceImp = new UserServiceImp();
                serviceImp.deleteProduit(Integer.parseInt(id));
                produitCB.setSelected(false);
                getAllProduit();
            } else {
                // ... user chose CANCEL or closed the dialog
                alert.close();
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

    public void connect(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("./../vue/signIn.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
