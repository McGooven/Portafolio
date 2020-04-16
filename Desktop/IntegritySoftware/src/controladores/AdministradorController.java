package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class AdministradorController implements Initializable {
    @FXML Button btnCloseAdministrador;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCloseAdministrador.setOnAction((event) ->{
            System.out.println("cerrando desde la pagina de administrador");
            Node source =(Node)event.getSource();
            Stage currentStage = (Stage)source.getScene().getWindow();
            currentStage.close();
        });
    }    
    
}
