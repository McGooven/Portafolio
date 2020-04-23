package controladores.AdrRol;

import controladores.StageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class,está directamente relacionado con el fxml en el paquete "vistas".
 *
 * @author Diego
 */
public class CrearCentroController implements Initializable {
    StageController stageController;
    @FXML TextField txtIdCent,txtCalle;
    @FXML ChoiceBox chcbReg,chcbCom,chcbCiu;
    @FXML Button btnConfirm;
    
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
