package controladores.AdrRol;

import controladores.StageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import controladores.PeticionJSON;
import javafx.scene.control.TableColumn;
import org.json.JSONObject;

/**
 * FXML Controller class, está directamente relacionado con el fxml en el paquete "vistas".
 *
 * @author Diego
 */
public class SedBoxManteController implements Initializable {
    StageController stageController;
    
    @FXML private ListView<?> ltvSedes;
    @FXML private Button btnConfirmar,btnLimpiar,btnCrearSede,btnEditarSede;
    @FXML private TableView<?> tbvBoxesSede;
    @FXML private TableColumn<?, ?> colId, colEstado, colHabilitada;
    @FXML private Pane panSedeCardContainer;
    @FXML private TextField txtFRegSede,txtFCiuSede,txtFCalleSede,txtFAnomSede;

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
