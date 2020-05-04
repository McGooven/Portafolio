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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class, está directamente relacionado con el fxml en el paquete "vistas".
 *
 * @author Diego
 */
public class SedBoxManteController implements Initializable {
    StageController stageController;
    
    @FXML private ListView<String> ltvSedes;
    @FXML private Button btnConfirmar,btnLimpiar,btnCrearSede,btnEditarSede;
    @FXML private TableView<Box> tbvBoxesSede;
    @FXML private TableColumn<Box, Integer> colId; 
    @FXML private TableColumn<Box, String> colEstado; 
    @FXML private TableColumn<Box, String> colHabilitada;
    @FXML private Pane panSedeCardContainer;
    @FXML private TextField txtFRegSede,txtFCiuSede,txtFCalleSede,txtFAnomSede;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PeticionJSON request = new PeticionJSON(new JSONObject(), "get", "http://localhost:3000/api/centros"); 
        request.connect();
        
        JSONArray boxes = request.res.getJSONObject(0).getJSONArray("centro");
        
        ObservableList<Box> list = FXCollections.observableArrayList(
                
        );
        
    }
    
    /**
     * Entregar una instancia de StageController a este controller.
     * @param c StageController Instance.
     */
    public void setStageController(StageController c){
        this.stageController = c;
        
    }
    
    public static class Box{
        private SimpleIntegerProperty idBox;
        private SimpleStringProperty habilitada;
        private SimpleStringProperty estado;
        
        public Box(Integer idBox, String habilitada, String estado) {
            this.idBox = new SimpleIntegerProperty(idBox);
            this.habilitada = new SimpleStringProperty(habilitada);
            this.estado = new SimpleStringProperty(estado);
        }

        public int getIdBox() {
            return idBox.get();
        }

        public void setIdBox(int idBox) {
            this.idBox.set(idBox);
        }

        public String getHabilitada() {
            return habilitada.get();
        }

        public void setHabilitada(String habilitada) {
            this.habilitada.set(habilitada);
        }

        public String getEstado() {
            return estado.get();
        }

        public void setEstado(String estado) {
            this.estado.set(estado);
        }
        
    }
    
}
