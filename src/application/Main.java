package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /* init context
        final DB db = Util.initDb();
        Util.initRoles(db);
        Util.ajoutAdmin(db);
        Util.closeConnection(db);
        */
        /* start AApplication */
        Parent root = FXMLLoader.load(getClass().getResource("./../vue/login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
