package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Utilisateur;

import java.io.IOException;
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

    public void programmation(ActionEvent event){


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

        System.out.println("GO...");


    }
}
