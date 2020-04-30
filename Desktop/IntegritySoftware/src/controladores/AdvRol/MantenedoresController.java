package controladores.AdvRol;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import controladores.PeticionJSON;
import controladores.StageController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;

/**
 * FXML Controller class
 *
 * @author Grupo x
 */
public class MantenedoresController implements Initializable {
    StageController stageController;
    PersonalFormController controllerFxml;
    PacienteFormController controllerFxml2;
    
    @FXML private Pane panContMantenedores,panContInfoUsuario;
    @FXML private GridPane grdPContMantUsuario;
    
    @FXML private TableView<RowUsuarios> tbvMantUsuario;
    @FXML private TableColumn<RowUsuarios, String> tblCRut;
    @FXML private TableColumn<RowUsuarios, String> tblCNombre;
    @FXML private TableColumn<RowUsuarios, String> tblCTipo;
    
    @FXML private Button btnGuardar,btnCancelar,btnSalir;
    @FXML private TextField txtBuscadorUsuario;
    @FXML private Label lblUsuario,lblStockInsumo,lblReporteInsumos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblUsuario.setOnMouseClicked(e -> { 
            lblUsuario.getStyleClass().set(0, "menuItemSelected");
            lblStockInsumo.getStyleClass().set(0, "menuItem");
            lblReporteInsumos.getStyleClass().set(0, "menuItem");
        });
        lblStockInsumo.setOnMouseClicked(e -> {
            lblUsuario.getStyleClass().set(0, "menuItem");
            lblStockInsumo.getStyleClass().set(0, "menuItemSelected");
            lblReporteInsumos.getStyleClass().set(0, "menuItem");
        });
        lblReporteInsumos.setOnMouseClicked(e -> {
            lblUsuario.getStyleClass().set(0, "menuItem");
            lblStockInsumo.getStyleClass().set(0, "menuItem");
            lblReporteInsumos.getStyleClass().set(0, "menuItemSelected");
        });
        
        PeticionJSON request = new PeticionJSON(new JSONObject(), "GET", "http://localhost:3000/api/usuarios");
        request.connect();
        ObservableList<RowUsuarios> list =rellenarTablaUsuarios(request.res);
        tblCRut.setCellValueFactory(new PropertyValueFactory<>("rut"));
        tblCNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tblCTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tbvMantUsuario.setItems(list);
        
        tbvMantUsuario.setOnMouseClicked((MouseEvent event) -> {
            //agregando Paneles
            try {
                controllerFxml = (PersonalFormController)this.stageController.addContent("FormularioPersonal", "/vistas/AdvRol/PersonalForm.fxml");
                controllerFxml2= (PacienteFormController)this.stageController.addContent("FormularioPaciente", "/vistas/AdvRol/PacienteForm.fxml");
                controllerFxml.setStageController(this.stageController);
                controllerFxml2.setStageController(this.stageController);
            } catch (IOException ex) {
                Logger.getLogger(MantenedoresController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                int index = tbvMantUsuario.getSelectionModel().getSelectedIndex();
                RowUsuarios row = tbvMantUsuario.getItems().get(index);
                String t=row.tipo.getValue();
                try{
                ReadContext ctx = JsonPath.parse(request.res.getJSONObject(0).toString());
                if(t.equals("Administrador") || t.equals("Administrativo") || t.equals("Enfermero") || t.equals("Medico")){
                    
                    List<String> rut= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.rutPersonal");
                    List<Integer> id= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+"')].idUsuario");
                    List<String> pNombre= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.pnombre");
                    List<String> sNombre= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.snombre");
                    List<String> pApellido= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.papellido");
                    List<String> sApellido= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.sapellido");
                    List<String> email= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+"')].correo");
                    List<String> titulo= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.profesions[0].titulo");
                    List<String> casaEstudio= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.profesions[0].casaEstudio");
                    List<String> direccion= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.direccionIdDireccion.direccion");
                    List<String> fecNac= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.nacPersonal");
                    List<String> fecIng= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.anioIngreso");
                    List<String> fecEgr= ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.profesions[0].fechaEgreso");
                    JSONArray regions = new JSONArray(((List)ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion")).toString());
                    JSONArray comuns = new JSONArray(((List)ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.direccionIdDireccion.comunaComuna")).toString());
                    JSONArray cargo = new JSONArray(((List)ctx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                        "')].personalIdPersonal.espInters[0]")).toString());                        
                    controllerFxml.setTxtRut(rut.get(0));
                    controllerFxml.setTxtId(id.get(0).toString());
                    controllerFxml.setTxtPNombre(pNombre.get(0));
                    controllerFxml.setTxtSNombre(sNombre.get(0));
                    controllerFxml.setTxtPApellido(pApellido.get(0));
                    controllerFxml.setTxtSApellido(sApellido.get(0));
                    controllerFxml.setTxtEmail(email.get(0));
                    controllerFxml.setTxtTitulo(titulo.get(0));
                    controllerFxml.setTxtCasaEstudio(casaEstudio.get(0));
                    controllerFxml.setTxtDireccion(direccion.get(0));
                    controllerFxml.setDtpFechaNacimiento(PeticionJSON.parseDate(fecNac.get(0)));
                    controllerFxml.setDtpFechaIngreso(PeticionJSON.parseDate(fecIng.get(0)));
                    controllerFxml.setDtpFechaEgreso(PeticionJSON.parseDate(fecEgr.get(0)));
                    ObservableList<JSONObject> regiones = FXCollections.observableArrayList();
                    regions.forEach((e) -> {
                        regiones.add((JSONObject)e);
                    });
                    
                    ObservableList<JSONObject> comunas = FXCollections.observableArrayList();
                    comuns.forEach((e)->{
                        comunas.add((JSONObject)e);
                    });
                    
                    ObservableList<JSONObject> cargos = FXCollections.observableArrayList();
                    cargo.forEach((e)->{
                        cargos.add((JSONObject)e);
                    });                    
                    
                    controllerFxml.setCmbBRegion(regiones);
                    controllerFxml.setCmbBComuna(comunas);
                    controllerFxml.setCmbBCargo(cargos);
                    
                    this.stageController.showContent(panContInfoUsuario, "FormularioPersonal");
                }else{
                    
                    
                    this.stageController.showContent(panContInfoUsuario, "FormularioPaciente");
                }
                }catch(Exception ex){
                    Logger.getLogger(MantenedoresController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
    }    
    
    public void setStageController(StageController c){
        this.stageController = c;
    }
    
    private Object agregarPaneles() {
        try {
            controllerFxml = (PersonalFormController)this.stageController.addContent("FormularioPersonal", "/vistas/AdvRol/PersonalForm.fxml");
            
        } catch (IOException ex) {
            Logger.getLogger(MantenedoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controllerFxml;
    }

    private ObservableList<RowUsuarios> rellenarTablaUsuarios(JSONArray res) {
        ObservableList<RowUsuarios> result = FXCollections.observableArrayList();
        JSONObject firstObject = res.getJSONObject(0);
        JSONArray Usuarios = firstObject.getJSONArray("rows");
        
        Usuarios.forEach((e) -> {
            JSONObject user=(JSONObject)e;
            result.add(new RowUsuarios((String)user.get("NOMBRE"),(String)user.get("RUT"),(String)user.get("TIPO")));
        });
        return result;
    }
    
    public static class RowUsuarios{
        private final SimpleStringProperty nombre;
        private final SimpleStringProperty rut;
        private final SimpleStringProperty tipo;

        public RowUsuarios(String nombre, String rut, String tipo) {
            this.nombre = new SimpleStringProperty(nombre);
            this.rut = new SimpleStringProperty(rut);
            this.tipo = new SimpleStringProperty(tipo);
        }

        public String getNombre() {
            return nombre.get();
        }
        public String getRut() {
            return rut.get();
        }
        public String getTipo() {
            return tipo.get();
        }
    }    
}
