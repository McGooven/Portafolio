package controladores.EnfRol;

import Model.Atencion;
import Model.Box;
import Model.Centro;
import Model.TipoSesion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import controladores.PeticionJSON;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import org.json.JSONArray;
import org.json.JSONObject;


public class AgendarSesionController implements Initializable {
    @FXML private JFXButton btnAgendar;
    @FXML private JFXComboBox<JSONObject> cmbBoxes;
    @FXML private JFXCheckBox chkBoolHrsExtras;
    @FXML private Pane pnlHrsExtras;
    @FXML private JFXTimePicker TmpHoraTermino;
    @FXML private JFXTreeTableView<RowMedicos> tblMedicos;
    @FXML private JFXTreeTableView<RowPacientes> tblPacientes;
    
    private String fechaIngreso;
    private String fechaTermino;
    private ObservableList<JSONObject> boxes;
    private JSONArray medicos;
    private JSONArray pacientes;
    private JSONObject TipoSesion;
    private int idCentro;
    private int idBox;
    private Box box = new Box();
    private Centro centro = new Centro();
    private ArrayList<String> selectedRutsM;
    private String selectedRut;
    
    private int index;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }   
    
    public void setVariables(String a, JSONObject c, int d, ObservableList<JSONObject> e, JSONArray f, JSONArray g){
        this.fechaIngreso = a;
        this.TipoSesion = c;
        this.boxes = e;
        this.medicos = f;
        this.pacientes = g;
        this.idCentro = d;
        
        pnlHrsExtras.setVisible(false);
        chkBoolHrsExtras.setOnAction((t)->{
            if(((JFXCheckBox)t.getSource()).isSelected()){
                pnlHrsExtras.setVisible(true);
            }else{
                pnlHrsExtras.setVisible(false);
                TmpHoraTermino.setValue(new LocalTimeStringConverter().fromString(""));
            }
        });
        tblMedicos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tblPacientes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        
        //rellenar elementos
        cmbBoxes.setVisibleRowCount(5);
        cmbBoxes.setPromptText("Id Box...");
        cmbBoxes.setConverter(new StringConverter<JSONObject>() {
            @Override
            public String toString(JSONObject object) {
                if (object == null){return null;} 
                
                else {return String.valueOf(object.getInt("idBox"));}
            }
            @Override
            public JSONObject fromString(String string) {
                return cmbBoxes.getItems().stream().filter(ap -> 
                        string.equals(ap.getInt("idBox")-1)).findFirst().orElse(null);
            }
        }); 
        cmbBoxes.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null){
                System.out.println("seleccionado box asdfa: "+newval);
                centro.setNombreSede(newval.getJSONObject("centroIdCentro").getString("nombreSede"));
                centro.setIdCentro(newval.getJSONObject("centroIdCentro").getInt("idCentro"));

                box.setIdBox(newval.getInt("idBox"));
                box.setEstado(newval.getString("estado"));
                box.setHabilitada(newval.getString("habilitada"));
                box.setCentroIdCentro(centro);
            }
        });
        this.cmbBoxes.setItems(boxes);
        
        
        //tablas
        JFXTreeTableColumn<RowMedicos, String> rutMedico = new JFXTreeTableColumn<>("rutMedico");
        rutMedico.setPrefWidth(90);
        rutMedico.setCellValueFactory((TreeTableColumn.CellDataFeatures<RowMedicos, String> param) -> 
                param.getValue().getValue().rutMedico);
        
        JFXTreeTableColumn<RowMedicos, String> nombreMedico = new JFXTreeTableColumn<>("nombreMedico");
        nombreMedico.setPrefWidth(180);
        nombreMedico.setCellValueFactory((TreeTableColumn.CellDataFeatures<RowMedicos, String> param) -> 
                param.getValue().getValue().nombreMedico);
        
        ObservableList<RowMedicos> medicos = FXCollections.observableArrayList();
        this.medicos.forEach((elem)-> {
            JSONObject obj = (JSONObject)elem;
            medicos.add(new RowMedicos(obj.getString("rut"), obj.getString("nombre")));
        });
        final TreeItem<RowMedicos> root_00 = new RecursiveTreeItem<RowMedicos>(medicos, RecursiveTreeObject::getChildren);
        tblMedicos.getColumns().setAll(rutMedico,nombreMedico);
        tblMedicos.setRoot(root_00);
        tblMedicos.setShowRoot(false);
            tblMedicos.setOnMouseClicked((MouseEvent event)->{
            if(event.getButton().equals(MouseButton.PRIMARY)){
                ObservableList<TreeItem<RowMedicos>> selectedItems = tblMedicos.getSelectionModel().getSelectedItems();
                
                selectedRutsM = new ArrayList<String>();
                for (TreeItem<RowMedicos> row : selectedItems) {
                   selectedRutsM.add(row.getValue().rutMedico.getValue());
                }
            }
            
        });
        
        
        
        JFXTreeTableColumn<RowPacientes, String> rutPaciente = new JFXTreeTableColumn<>("rutPaciente");
        rutPaciente.setPrefWidth(90);
        rutPaciente.setCellValueFactory((TreeTableColumn.CellDataFeatures<RowPacientes, String> param) -> 
                param.getValue().getValue().rutPaciente);
        
        JFXTreeTableColumn<RowPacientes, String> nombrePaciente = new JFXTreeTableColumn<>("nombrePaciente");
        nombrePaciente.setPrefWidth(180);
        nombrePaciente.setCellValueFactory((TreeTableColumn.CellDataFeatures<RowPacientes, String> param) -> 
                param.getValue().getValue().nombrePaciente);
        ObservableList<RowPacientes> pacientes = FXCollections.observableArrayList();
        this.pacientes.forEach((elem)-> {
            JSONObject obj = (JSONObject)elem;
            pacientes.add(new RowPacientes(obj.getString("rut"), obj.getString("nombre")));
        });
        final TreeItem<RowPacientes> root_01 = new RecursiveTreeItem<RowPacientes>(pacientes, RecursiveTreeObject::getChildren);
        tblPacientes.getColumns().setAll(rutPaciente,nombrePaciente);
        tblPacientes.setRoot(root_01);
        tblPacientes.setShowRoot(false);
        tblPacientes.setOnMouseClicked((MouseEvent event)->{
            if(event.getButton().equals(MouseButton.PRIMARY)){
                int index = tblPacientes.getSelectionModel().getSelectedIndex();
                RowPacientes selectedItem = tblPacientes.getSelectionModel().getSelectedItem().getValue();
                selectedRut = selectedItem.rutPaciente.getValue();
            } 
        });
        

        btnAgendar.setOnAction((elem)->{
            if(!selectedRutsM.isEmpty() && !selectedRut.isEmpty() && !cmbBoxes.getValue().isEmpty()){
                try {
                    JSONArray result = new JSONArray();
                    Atencion atencion = new Atencion();
                    TipoSesion tiposesion= new TipoSesion();

                    tiposesion.setIdTpSn(this.TipoSesion.getInt("idTpSn"));
                    tiposesion.setNombreTpSn(this.TipoSesion.getString("nombreTpSn"));

                    atencion.setIdAtencion(null);
                    atencion.setBoxIdBox(box);
                    atencion.setFechaIngreso(this.fechaIngreso);
                    atencion.setSituacion(0);
                    atencion.setTipoSesionIdTpSn(tiposesion);
                    
                    JSONObject object = new JSONObject(atencion);
                    result.put(object);
                    
                    JSONObject mixtos = new JSONObject();
                    mixtos.put("rutPaciente", this.selectedRut);
                    mixtos.put("rutPersonales", this.selectedRutsM);
                    result.put(mixtos);
                    
                    JSONObject results = new JSONObject();
                    results.put("resultados", result);
                    PeticionJSON request = new PeticionJSON(results, "post", "http://localhost:3000/api/saveAtencion");
                    request.connect();
                } catch (Exception ex) {
                    Logger.getLogger(BaseEnfermeroController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                String mensaje = "Porfavor seleccione algun Medico y un paciente";
            }
        });
    }
    
    
    class RowMedicos extends RecursiveTreeObject<RowMedicos>{
        StringProperty rutMedico;
        StringProperty nombreMedico;
        
        public RowMedicos(String rut, String nombre){
            this.rutMedico = new SimpleStringProperty(rut);
            this.nombreMedico = new SimpleStringProperty(nombre);
        }
    }
        
    class RowPacientes extends RecursiveTreeObject<RowPacientes>{
        StringProperty rutPaciente;
        StringProperty nombrePaciente;
        
        public RowPacientes(String rut, String nombre){
            this.rutPaciente = new SimpleStringProperty(rut);
            this.nombrePaciente = new SimpleStringProperty(nombre);
        }
    }
}
