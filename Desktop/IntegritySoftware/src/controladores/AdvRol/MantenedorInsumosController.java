package controladores.AdvRol;

import controladores.StageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Grupo x
 */
public class MantenedorInsumosController implements Initializable {
    StageController stageController;
    
    @FXML
    private GridPane grdPContMantUsuario;

    @FXML
    private TableView<?> tbvMantInsumos;

    @FXML
    private Pane panContInfoInsumo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtBuscadorInsumos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void setStageController(StageController c){
        this.stageController = c;
    }
    
}
