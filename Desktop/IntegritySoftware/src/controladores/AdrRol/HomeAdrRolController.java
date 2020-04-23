package controladores.AdrRol;

import controladores.StageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class, está directamente relacionado con el fxml en el paquete "vistas".
 *
 * @author Diego
 */
public class HomeAdrRolController implements Initializable {
    StageController stageController;
    
    @FXML Button btnCrCent,btnEdCent,btnCrBox,btnEdBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCrCent.setOnAction((event) -> {
            stageController.changeScene("CrearCent");
        });
    }
    
    /**
     * Entregar una instancia de StageController a este controller.
     * @param c StageController Instance.
     */
    public void setStageController(StageController c){
        this.stageController = c;
    }
    
}
