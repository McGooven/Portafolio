package controladores.AdrRol;

import controladores.StageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class, está directamente relacionado con el fxml en el paquete "vistas".
 *
 * @author Diego
 */
public class EditarBoxController implements Initializable {
    StageController stageController;

    @FXML Button btnDesCent;
    @FXML ChoiceBox chcbCent,chcbIdBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    /**
     * Entregar una instancia de StageController a este controller.
     * @param c StageController Instance.
     */
    public void setStageController(StageController c){
        this.stageController = c;
    }
}
