package controladores.AdrRol;

import controladores.StageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class,está directamente relacionado con el fxml en el paquete "vistas".
 * @author Diego
 */
public class CrearBoxController implements Initializable {
    StageController stageController;
    
    @FXML TextField txtIdBox;
    @FXML ChoiceBox chcbCent,chcbEstado;
    @FXML Button btnConfirm;
    @FXML ImageView imgvLogo;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgvLogo.setOnMouseClicked((event) -> {
            stageController.stageOff();
            //stageController.changeScene("Home");
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
