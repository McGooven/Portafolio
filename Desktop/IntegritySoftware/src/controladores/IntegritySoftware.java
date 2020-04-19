package controladores;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class IntegritySoftware extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = loader.load(getClass().getResourceAsStream("/vistas/Login.fxml"));
            
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Bienvenido");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            //Logger.getLogger(IntegritySoftware.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
