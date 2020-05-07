package controladores.AdrRol;

import com.jayway.jsonpath.JsonPath;
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
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
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
    ObservableList<RowSedes> list;
    
    @FXML private ListView<JSONObject> ltvSedes;
    @FXML private Button btnConfirmar,btnLimpiar,btnCrearSede,btnEditarSede;
    @FXML private TableView<Box> tbvBoxesSede;
    //@FXML private TableColumn<RowSedes, String> colId;
    //@FXML private TableColumn<RowSedes, String> colEstado;
    //@FXML private TableColumn<RowSedes, String> colHabilitada;
    @FXML private TableColumn<Box, Integer> colId; 
    @FXML private TableColumn<Box, String> colEstado; 
    @FXML private TableColumn<Box, String> colHabilitada;
    @FXML private Pane panSedeCardContainer;
    @FXML private TextField txtFCalleSede,txtFAnomSede;
    @FXML private ComboBox cmbRegion, cmbComuna;

    
    ReadContext rtx;
    WriteContext wtx;
    
   
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PeticionJSON request = new PeticionJSON(new JSONObject(), "get", "http://localhost:3000/api/centros"); 
        request.connect();
        
        JSONArray rows = request.res.getJSONObject(0).getJSONArray("listView");
        

        
        ltvSedes.setItems(rellenarListview(rows));
        rtx = JsonPath.parse(request.res.getJSONObject(0).toString());
        wtx = JsonPath.parse(request.res.getJSONObject(0).toString());
        

        
        ltvSedes.setCellFactory(param -> new ListCell<JSONObject>() {
            @Override
            protected void updateItem(JSONObject item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getString("nombreSede")== null) {
                    setText(null);
                } else {
                    setText(item.getString("nombreSede"));
                }
            }
        }
        );
        
        ltvSedes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<JSONObject>() {
            @Override
            public void changed(ObservableValue<? extends JSONObject> observable, JSONObject oldValue, JSONObject newValue) {
                System.out.println("ListView selection changed from oldValue = " 
                        + oldValue + " to newValue = " + newValue.getInt("idCentro"));
            }
        });
        
        
        
        
       
    }
    
    
    private ObservableList<JSONObject> rellenarListview(JSONArray res) {
        ObservableList<JSONObject> result = FXCollections.observableArrayList();
        res.forEach((e)->  {
            result.add((JSONObject)e);
        
        });
        return result;
        
    }
    
    private void infoSede(int i){
        
         
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
    
        //limpiar
        public void limpiarFormulario(){
        this.txtFCalleSede.clear();
        this.txtFAnomSede.clear();
        this.cmbRegion.getSelectionModel().clearSelection();
        this.cmbComuna.getSelectionModel().clearSelection();
        }
    
}
