package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    
    @FXML TextField TxtUsuario;
    @FXML PasswordField PswContrasenia;
    @FXML Button btnIngresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnIngresar.setOnAction((event) -> {
            Node source =(Node)event.getSource();
            Stage currentStage = (Stage)source.getScene().getWindow();
            System.out.println(currentStage);
            currentStage.close();
            try {
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                AnchorPane root = loader.load(getClass().getResourceAsStream("/vistas/CrearUsuario.fxml"));
                Scene scene = new Scene(root);
                newStage.setTitle("Administrador");
                newStage.setScene(scene);
                newStage.show();
            } catch (Exception ex) {
                Logger.getLogger(IntegritySoftware.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }    
}
