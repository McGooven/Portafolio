package controladores.AdrRol;

import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.WriteContext;
import controladores.AdvRol.MantenedoresController;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;
import org.json.JSONArray;
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

    
    ReadContext rtx;
    WriteContext wtx;
    
   
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    PeticionJSON request = new PeticionJSON(new JSONObject(), "GET", "http://localhost:3000/api/usuarios");
    request.connect();
     ObservableList<SedBoxManteController.RowSedes> list =(request.res);
    
    ltvSedes.setOnMouseClicked((MouseEvent event)-> {
        
        //ventana add box
        
    });
    }
    
    private ObservableList<RowSedes> rellenarSedes(JSONArray res) {
        ObservableList<RowSedes> result = FXCollection.observableArrayList();
        JSONObject firstObject = res.getJSONObject(0);
        JSONArray Usuarios = firstObject.getJSONArray("rows");
        
        Usuarios.forEach((e) -> {
            JSONObject user=(JSONObject)e;
            result.add(new RowSedes((String)user.get("SEDES")));
        });
        return result;
    }
    
    public static class RowSedes{
        private final SimpleStringProperty sedes;


        public RowSedes(String sedes) {
            this.sedes = new SimpleStringProperty(sedes);

        }

        public String getSedes() {
            return sedes.get();

     }  
        } 
    
    /**
     * Entregar una instancia de StageController a este controller.
     * @param c StageController Instance.
     */
    public void setStageController(StageController c){
        this.stageController = c;
    }
    
}
