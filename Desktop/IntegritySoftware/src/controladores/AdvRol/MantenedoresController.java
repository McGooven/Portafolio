package controladores.AdvRol;

import Model.Centro;
import Model.Comuna;
import Model.Direccion;
import Model.EspInter;
import Model.FichaPaciente;
import Model.Personal;
import Model.Profesion;
import Model.Region;
import Model.Usuario;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.WriteContext;
import controladores.PeticionJSON;
import controladores.StageController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.ListExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONException;
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
    private PeticionJSON tableRequest;
    ObservableList<RowUsuarios> list = FXCollections.observableArrayList();
    ObservableList<RowUsuarios> filteredData = FXCollections.observableArrayList();
    private int tipoFormulario;
    
    int modificando=0;
    int creando = 0;
    
    int index;
    
    ReadContext rtx;
    WriteContext wtx;
    
    @FXML private Pane panContMantenedores,panContInfoUsuario;
    @FXML private GridPane grdPContMantUsuario;
    
    @FXML private TableView<RowUsuarios> tbvMantUsuario;
    @FXML private TableColumn<RowUsuarios, String> tblCRut;
    @FXML private TableColumn<RowUsuarios, String> tblCNombre;
    @FXML private TableColumn<RowUsuarios, String> tblCTipo;
    
    @FXML private Button btnGuardar,btnCancelar,btnSalir,btnAgregar,btnModificar, btnEliminar;
    @FXML private TextField txtBuscadorUsuario;
    @FXML private Label lblUsuario,lblStockInsumo,lblReporteInsumos,lblBuscador;
    
    @FXML
    void habilitarCambios(ActionEvent event) {
        estadoModificando();
    }
    
    public MantenedoresController() {
            tableRequest = new PeticionJSON(new JSONObject(), "GET", "http://localhost:3000/api/usuarios");
            tableRequest.connect();
            // Add some sample data to the master data
            list = rellenarTablaUsuarios(this.tableRequest.res);

            // Initially add all data to filtered data
            filteredData.addAll(list);

            // Listen for changes in master data.
            // Whenever the master data changes we must also update the filtered data.
            list.addListener(new ListChangeListener<RowUsuarios>() {
                    @Override
                    public void onChanged(ListChangeListener.Change<? extends RowUsuarios> change) {
                            updateFilteredData();
                    }
            });
    }
    
    private void createUsuarioPaciente(){
        JSONArray result = new JSONArray();
        EspInter cargo = new EspInter();
        cargo.setIdEspecialidad(7); //cargo de paciente(no usar el 4 que es para los auxiliares y no se muestran en el sistema)
        Region region          = new Region();
        Comuna comuna          = new Comuna();
        Direccion direccion    = new Direccion();
        Centro centro          = new Centro();
        FichaPaciente paciente = new FichaPaciente();
        Usuario usuario        = new Usuario();
        
        centro.setIdCentro(controllerFxml2.getCmbCentro().getInt("idCentro"));
        centro.setNombreSede(controllerFxml2.getCmbCentro().getString("nombreSede"));
        
        region.setIdRegion(controllerFxml2.getCmbRegion().getInt("idRegion"));
        region.setNombre(controllerFxml2.getCmbRegion().getString("nombre"));
        
        comuna.setIdComuna(controllerFxml2.getCmbComuna().getInt("idComuna"));
        comuna.setNombreComuna(controllerFxml2.getCmbComuna().getString("nombreComuna"));
        comuna.setRegionIdRegion(region);
        
        direccion.setIdDireccion(null);
        direccion.setDireccion(controllerFxml2.getTxtCalle());
        direccion.setComunaComuna(comuna);
        
        paciente.setCentroIdCentro(centro);
        paciente.setRutPaciente(controllerFxml2.getTxtRut());
        paciente.setEtapa(Integer.parseInt(controllerFxml2.getTxtEtapa()));
        paciente.setDireccionIdDireccion(direccion);
        paciente.setHabilitado("S");
        paciente.setImagePaciente(null);
        paciente.setNacPaciente(controllerFxml2.getDmpFechaNacimiento());
        paciente.setPnombre(controllerFxml2.getTxtPrimerNombre());
        paciente.setSnombre(controllerFxml2.getTxtSegundoNombre());
        paciente.setPapellido(controllerFxml2.getTxtPrimerApellido());
        paciente.setSapellido(controllerFxml2.getTxtSegundoApellido());
        
        usuario.setPassword(controllerFxml2.getTxtCorreo().substring(0, 3)+
                controllerFxml2.getTxtRut().substring(controllerFxml2.getTxtRut().length()-1));
        usuario.setFichaPacienteIdFicha(paciente);
        usuario.setHabilitado("S");
        usuario.setPermisos(cargo);
        usuario.setCorreo(controllerFxml2.getTxtCorreo());
        usuario.setIdUsuario(null);
        
        try {
            JSONObject object = new JSONObject(usuario);
            result.put(object);
            PeticionJSON request = new PeticionJSON(result.getJSONObject(0), "post", "http://localhost:3000/api/usuario/C");
            request.connect();

            JSONArray array = new JSONObject(rtx.jsonString()).getJSONArray("UsuariosObj");
            array.put(request.res.getJSONObject(0).getJSONObject("newUser"));
            wtx.add("$.UsuariosObj", new net.minidev.json.JSONArray().addAll(array.toList()));
            rtx = JsonPath.parse(wtx.jsonString());
            list.setAll(rellenarTablaUsuarios(request.res.getJSONArray(1)));
        } catch (JSONException e) {
            e.printStackTrace();
        }        
    }
    
    private void createUsuarioPersonal() {
        if(rtx != null && wtx != null){
            JSONArray result    = new JSONArray();
            EspInter cargo      = new EspInter();
            Personal personal   = new Personal();
            Region region       = new Region();
            Comuna comuna       = new Comuna();
            Direccion direccion = new Direccion();
            Usuario usuario     = new Usuario();
            Profesion profesion = new Profesion();
            Centro centro       = new Centro();
            
            cargo.setIdEspecialidad(controllerFxml.getCmbBCargo().getInt("idEspecialidad"));
            cargo.setNombre(controllerFxml.getCmbBCargo().getString("nombre"));
            
            region.setIdRegion(controllerFxml.getCmbBRegion().getInt("idRegion"));
            region.setNombre(controllerFxml.getCmbBRegion().getString("nombre"));
            
            comuna.setIdComuna(controllerFxml.getCmbBComuna().getInt("idComuna"));
            comuna.setNombreComuna(controllerFxml.getCmbBComuna().getString("nombreComuna"));
            comuna.setRegionIdRegion(region);
            
            direccion.setIdDireccion(null);
            direccion.setDireccion(controllerFxml.getTxtDireccion());
            direccion.setComunaComuna(comuna);
            
            centro.setIdCentro(controllerFxml.getCmbBSede().getInt("idCentro"));
            centro.setNombreSede(controllerFxml.getCmbBSede().getString("nombreSede"));
            
            personal.setIdPersonal(null);
            personal.setRutPersonal(controllerFxml.getTxtRut());
            personal.setImagePersonal(null);
            personal.setNacPersonal(controllerFxml.getDtpFechaNacimiento());
            personal.setPnombre(controllerFxml.getTxtPNombre());
            personal.setSnombre(controllerFxml.getTxtSNombre());
            personal.setPapellido(controllerFxml.getTxtPApellido());
            personal.setSapellido(controllerFxml.getTxtSApellido());
            personal.setAnioIngreso(controllerFxml.getDtpFechaIngreso());
            
            ArrayList<Profesion> profesiones = new ArrayList<>();
            ArrayList<EspInter> cargos = new ArrayList<>();
            cargos.add(cargo);
            profesiones.add(profesion);
            
            personal.setEspInters(cargos);
            personal.setProfesions(profesiones);
            personal.setCentroIdCentro(centro);
            personal.setDireccionIdDireccion(direccion);
            personal.setHabilitado("S ");
            
            profesion.setIdProfesion(null);
            profesion.setCertificado(null);
            profesion.setTitulo(controllerFxml.getTxtTitulo());
            profesion.setFechaEgreso(controllerFxml.getDtpFechaEgreso());
            profesion.setCasaEstudio(controllerFxml.getTxtCasaEstudio());
            //profesion.setPersonalIdPersonal(personal);
            
            usuario.setIdUsuario(null);
            usuario.setCorreo(controllerFxml.getTxtEmail());
            usuario.setHabilitado("S");
            usuario.setPassword(controllerFxml.getTxtEmail().substring(0, 3)+
                controllerFxml.getTxtRut().substring(controllerFxml.getTxtRut().length()-1));
            usuario.setPermisos(cargo);
            usuario.setPersonalIdPersonal(personal);
            try {
                JSONObject object = new JSONObject(usuario);
                result.put(object);
                PeticionJSON request = new PeticionJSON(result.getJSONObject(0), "post", "http://localhost:3000/api/usuario/C");
                request.connect();
                
                JSONArray array = new JSONObject(rtx.jsonString()).getJSONArray("UsuariosObj");
                array.put(request.res.getJSONObject(0).getJSONObject("newUser"));
                wtx.add("$.UsuariosObj", new net.minidev.json.JSONArray().addAll(array.toList()));
                rtx = JsonPath.parse(wtx.jsonString());
                list.setAll(rellenarTablaUsuarios(request.res.getJSONArray(1)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    private void updateUsuarioPersonal(int i ) {
        String rut = controllerFxml.getTxtRut();
        if(rtx != null && wtx != null){
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.pnombre", controllerFxml.getTxtPNombre());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.snombre", controllerFxml.getTxtSNombre());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.papellido", controllerFxml.getTxtPApellido());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.sapellido", controllerFxml.getTxtSApellido());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.nacPersonal", controllerFxml.getDtpFechaNacimiento());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.anioIngreso", controllerFxml.getDtpFechaIngreso());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".correo", controllerFxml.getTxtEmail());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion", new net.minidev.json.JSONObject(controllerFxml.getCmbBRegion().toMap()));
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.direccionIdDireccion.comunaComuna", new net.minidev.json.JSONObject(controllerFxml.getCmbBComuna().toMap()));
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.direccionIdDireccion.direccion", controllerFxml.getTxtDireccion());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.profesions[0].titulo", controllerFxml.getTxtTitulo());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.profesions[0].casaEstudio", controllerFxml.getTxtCasaEstudio());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.profesions[0].fechaEgreso", controllerFxml.getDtpFechaEgreso());
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.espInters[0]", new net.minidev.json.JSONObject(controllerFxml.getCmbBCargo().toMap()));
            wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.centroIdCentro", new net.minidev.json.JSONObject(controllerFxml.getCmbBSede().toMap()));

            JSONObject json1 = new JSONArray(((List)JsonPath.parse(wtx.jsonString())
                .read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"))
                .toString())
                .getJSONObject(0);
            JSONObject json2= new JSONArray(((List)rtx
                .read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"))
                .toString())
                .getJSONObject(0);

            if(json1.similar(json2)){
                System.out.println("son iguales");
            }else{
                System.out.println("son diferentes");

                PeticionJSON request = new PeticionJSON(json1, "post", "http://localhost:3000/api/usuario/U");
                request.connect();
                wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]",
                        new net.minidev.json.JSONObject(request.res.getJSONObject(0).toMap()));
                rtx = JsonPath.parse(wtx.jsonString());

                list.setAll(rellenarTablaUsuarios(request.res.getJSONArray(1)));
            }        
        }
    }

    private void updateUsuarioPaciente(int i ) {
        String rut = controllerFxml2.getTxtRut();
        if(rtx != null && wtx != null){
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".fichaPacienteIdFicha.pnombre", controllerFxml2.getTxtPrimerNombre());
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".fichaPacienteIdFicha.snombre", controllerFxml2.getTxtSegundoNombre());
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".fichaPacienteIdFicha.papellido", controllerFxml2.getTxtPrimerApellido());
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".fichaPacienteIdFicha.sapellido", controllerFxml2.getTxtSegundoApellido());
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".fichaPacienteIdFicha.nacPaciente", controllerFxml2.getDmpFechaNacimiento());
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".fichaPacienteIdFicha.etapa", Integer.parseInt(controllerFxml2.getTxtEtapa()));
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".correo", controllerFxml2.getTxtCorreo());
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPersonal == '"+rut+"')]"
                    +".fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.regionIdRegion", new net.minidev.json.JSONObject(controllerFxml2.getCmbRegion().toMap()));
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPersonal == '"+rut+"')]"
                    +".fichaPacienteIdFicha.direccionIdDireccion.comunaComuna", new net.minidev.json.JSONObject(controllerFxml2.getCmbComuna().toMap()));
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPersonal == '"+rut+"')]"
                    +".fichaPacienteIdFicha.centroIdCentro", new net.minidev.json.JSONObject(controllerFxml2.getCmbCentro().toMap()));
            wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".fichaPacienteIdFicha.direccionIdDireccion.direccion", controllerFxml2.getTxtCalle());  

            JSONObject json1 = new JSONArray(((List)JsonPath.parse(wtx.jsonString())
                .read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"))
                .toString())
                .getJSONObject(0);
            JSONObject json2= new JSONArray(((List)rtx
                .read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"))
                .toString())
                .getJSONObject(0);
            System.out.println(json1);
            System.out.println(json2);

            if(json1.similar(json2)){
                System.out.println("son iguales");
            }else{
                System.out.println("son diferentes");

                PeticionJSON request = new PeticionJSON(json1, "post", "http://localhost:3000/api/usuario/U");
                request.connect();
                wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]",
                        new net.minidev.json.JSONObject(request.res.getJSONObject(0).toMap()));
                rtx = JsonPath.parse(wtx.jsonString());
                System.out.println(rtx.jsonString());

                 list.setAll(rellenarTablaUsuarios(request.res.getJSONArray(1)));
            }        
        }
    }
        
    private void deshabilitarUsuario(int i){
        RowUsuarios row = tbvMantUsuario.getItems().get(index);    
        String rut = row.getRut();

        if(rtx != null && wtx != null && index >= 0 ){
            if (tipoFormulario == 1) {
                wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    +".personalIdPersonal.habilitado", "N ");
                wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"
                    + ".habilitado", "N");

                JSONObject json1 = new JSONArray(((List)JsonPath.parse(wtx.jsonString())
                .read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]"))
                .toString())
                .getJSONObject(0);                      
                PeticionJSON request = new PeticionJSON(json1, "post", "http://localhost:3000/api/usuario/D");
                request.connect();
                wtx.set("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+rut+"')]",
                        new net.minidev.json.JSONObject(request.res.getJSONObject(0).toMap()));
                rtx = JsonPath.parse(wtx.jsonString());
                list.setAll(rellenarTablaUsuarios(request.res.getJSONArray(1)));

            }else{
                wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    +".fichaPacienteIdFicha.habilitado", "N");
                wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"
                    + ".habilitado", "N");

                JSONObject json1 = new JSONArray(((List)JsonPath.parse(wtx.jsonString())
                .read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]"))
                .toString())
                .getJSONObject(0);                      
                PeticionJSON request = new PeticionJSON(json1, "post", "http://localhost:3000/api/usuario/D");
                request.connect();
                wtx.set("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+rut+"')]",
                        new net.minidev.json.JSONObject(request.res.getJSONObject(0).toMap()));
                rtx = JsonPath.parse(wtx.jsonString());
                list.setAll(rellenarTablaUsuarios(request.res.getJSONArray(1)));
                
            }       
        }           
    }

        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estadoVacío();
        //btnModificar ejecuta "habilitarCambios()"
        btnGuardar.setOnAction((e)->{
            int index = tbvMantUsuario.getSelectionModel().getSelectedIndex();
            if(tipoFormulario == 1){
                if(modificando == 1 && controllerFxml != null){
                    //para modificar usuario personal
                    updateUsuarioPersonal(index);
                    tbvMantUsuario.refresh();
                    estadoVacío();
                }
                if(creando == 1 && controllerFxml != null){
                    //para crear usuario personal
                    createUsuarioPersonal();
                    tbvMantUsuario.refresh();
                    estadoVacío();
                }
            }else{
                //para pacientes
                if(modificando == 1 && controllerFxml2 != null){
                    updateUsuarioPaciente(index);
                    tbvMantUsuario.refresh();
                    estadoVacío();                    
                }
                if(creando == 1 && controllerFxml2 != null){
                    //para crear usuario paciente
                    createUsuarioPaciente();
                    tbvMantUsuario.refresh();
                    estadoVacío();
                }
            }  
        });
        btnAgregar.setOnAction((e)->{
            estadoCreando();
        });
        btnCancelar.setOnAction((e) -> {
            estadoVacío();
        });
        
        btnSalir.setOnAction((e) -> {
            stageController.stageOff();
        });
        
        btnEliminar.setOnAction((e) -> {
            int index = tbvMantUsuario.getSelectionModel().getSelectedIndex();
            deshabilitarUsuario(index);
        });
        
        
        
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
        lblBuscador.setOnMouseClicked((e)->{
            filteredData.setAll(list);
        });
        
        txtBuscadorUsuario.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.ENTER)){
                    updateFilteredData();
                }
            }
        });
        
        tblCRut.setCellValueFactory(new PropertyValueFactory<>("rut"));
        tblCNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tblCTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tbvMantUsuario.setItems(filteredData);
        
        rtx = JsonPath.parse(tableRequest.res.getJSONObject(0).toString());
        wtx = JsonPath.parse(tableRequest.res.getJSONObject(0).toString());
        
        tbvMantUsuario.setOnMouseClicked((MouseEvent event) -> {
            //agregando Paneles
            try {
                if(!this.stageController.searchForContent("FormularioPersonal")){
                    controllerFxml = (PersonalFormController)this.stageController.addContent("FormularioPersonal", "/vistas/AdvRol/PersonalForm.fxml");
                    controllerFxml.setStageController(this.stageController);
                }else{ controllerFxml.AnchorParent.setDisable(true);}
                if(!this.stageController.searchForContent("FormularioPaciente")){
                    controllerFxml2= (PacienteFormController)this.stageController.addContent("FormularioPaciente", "/vistas/AdvRol/PacienteForm.fxml");                    
                    controllerFxml2.setStageController(this.stageController);
                }else{
                    
                    controllerFxml2.AnchorParent.setDisable(true);}
                
            } catch (IOException ex) {
                Logger.getLogger(MantenedoresController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (event.getButton().equals(MouseButton.PRIMARY)) {  
                index = tbvMantUsuario.getSelectionModel().getSelectedIndex();
                if(index >= 0 ){
                    try{                
                        btnModificar.setDisable(false);
                        btnGuardar.setDisable(true);
                        btnCancelar.setDisable(true);
                        modificando=0;

                        RowUsuarios row = tbvMantUsuario.getItems().get(index);
                        String t=row.tipo.getValue();
                        if(t.equals("Administrador") || t.equals("Administrativo") || t.equals("Enfermero") || t.equals("Medico")){
                            tipoFormulario=1;
                        }else{
                            tipoFormulario=2;
                        }
                        if (event.getClickCount() == 2){
                            btnModificar.fire();
                        }

                        if(t.equals("Administrador") || t.equals("Administrativo") || t.equals("Enfermero") || t.equals("Medico")){

                            List<String> rut= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.rutPersonal");
                            List<Integer> id= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+"')].idUsuario");
                            List<String> pNombre= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.pnombre");
                            List<String> sNombre= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.snombre");
                            List<String> pApellido= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.papellido");
                            List<String> sApellido= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.sapellido");
                            List<String> email= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+"')].correo");
                            List<String> titulo= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.profesions[0].titulo");
                            List<String> casaEstudio= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.profesions[0].casaEstudio");
                            List<String> direccion= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.direccionIdDireccion.direccion");
                            List<String> fecNac= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.nacPersonal");
                            List<String> fecIng= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.anioIngreso");
                            List<String> fecEgr= rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.profesions[0].fechaEgreso");
                            JSONArray region = new JSONArray(((List)rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion")).toString());
                            JSONArray comuna = new JSONArray(((List)rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.direccionIdDireccion.comunaComuna")).toString());
                            JSONArray cargo = new JSONArray(((List)rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.espInters[0]")).toString());
                            JSONArray centro = new JSONArray(((List)rtx.read("$.UsuariosObj[?(@.personalIdPersonal.rutPersonal == '"+row.rut.getValue()+
                                "')].personalIdPersonal.centroIdCentro")).toString());
                            JSONArray regions = new JSONArray(((List)rtx.read("$.regiones[*]")).toString());
                            JSONArray comuns = new JSONArray(((List)rtx.read("$.comunas[*]")).toString());
                            JSONArray cargos = new JSONArray(((List)rtx.read("$.cargos[*]")).toString());    
                            JSONArray centros = new JSONArray(((List)rtx.read("$.centro[*]")).toString()); 
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
                            ObservableList<JSONObject> listRegiones = FXCollections.observableArrayList();
                            regions.forEach((e) -> {
                                listRegiones.add((JSONObject)e);
                            });

                            ObservableList<JSONObject> listComunas = FXCollections.observableArrayList();
                            comuns.forEach((e)->{
                                listComunas.add((JSONObject)e);
                            });

                            ObservableList<JSONObject> listCargos = FXCollections.observableArrayList();
                            cargos.forEach((e)->{
                                listCargos.add((JSONObject)e);
                            });
                            ObservableList<JSONObject> listCentros = FXCollections.observableArrayList();
                            centros.forEach((e)->{
                                listCentros.add((JSONObject)e);
                            });                    

                            controllerFxml.setCmbBRegion(listRegiones);
                            controllerFxml.setCmbBComuna(listComunas);
                            controllerFxml.setCmbBCargo(listCargos);
                            controllerFxml.setCmbBSede(listCentros);

                            controllerFxml.setRegionValue(region.getJSONObject(0));
                            controllerFxml.setComunaValue(comuna.getJSONObject(0));
                            controllerFxml.setCargoValue(cargo.getJSONObject(0));
                            controllerFxml.setSedeValue(centro.getJSONObject(0));

                            this.stageController.showContent(panContInfoUsuario, "FormularioPersonal");
                        }else{                    
                            List<String> rut= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.rutPaciente");
                            List<Integer> id= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+"')]fichaPacienteIdFicha.idFicha");
                            List<String> primerNombre= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.pnombre");
                            List<String> segundoNombre= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.snombre");
                            List<String> primerApellido= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.papellido");
                            List<String> segundoApellido= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.sapellido");
                            List<String> email= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+"')].correo");
                            List<Integer> etapa= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+"')]fichaPacienteIdFicha.etapa");
                            List<String> fecNac= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.nacPaciente");
                            List<String> calle= rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.direccionIdDireccion.direccion");
                            JSONArray region = new JSONArray(((List)rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.regionIdRegion")).toString());
                            JSONArray comuna = new JSONArray(((List)rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.direccionIdDireccion.comunaComuna")).toString());
                            JSONArray centro = new JSONArray(((List)rtx.read("$.UsuariosObj[?(@.fichaPacienteIdFicha.rutPaciente == '"+row.rut.getValue()+
                                "')].fichaPacienteIdFicha.centroIdCentro")).toString());
                            JSONArray regions = new JSONArray(((List)rtx.read("$.regiones[*]")).toString());
                            JSONArray comuns = new JSONArray(((List)rtx.read("$.comunas[*]")).toString());
                            JSONArray cent = new JSONArray(((List)rtx.read("$.centro[*]")).toString());

                            controllerFxml2.setTxtRut(rut.get(0));
                            controllerFxml2.setTxtIdFicha(id.get(0).toString());
                            controllerFxml2.setTxtPrimerNombre(primerNombre.get(0));
                            controllerFxml2.setTxtSegundoNombre(segundoNombre.get(0));
                            controllerFxml2.setTxtPrimerApellido(primerApellido.get(0));
                            controllerFxml2.setTxtSegundoApellido(segundoApellido.get(0));
                            controllerFxml2.setTxtCorreo(email.get(0));
                            controllerFxml2.setTxtEtapa(etapa.get(0).toString());
                            controllerFxml2.setDmpFechaNacimiento(PeticionJSON.parseDate(fecNac.get(0)));
                            controllerFxml2.setTxtCalle(calle.get(0));
                            
                            ObservableList<JSONObject> listRegiones = FXCollections.observableArrayList();
                            regions.forEach((e) -> {
                                listRegiones.add((JSONObject)e);
                            });
                            
                            ObservableList<JSONObject> listComunas = FXCollections.observableArrayList();
                            comuns.forEach((e)->{
                                listComunas.add((JSONObject)e);
                            });
                            
                            ObservableList<JSONObject> listCentros = FXCollections.observableArrayList();
                            cent.forEach((e)->{
                                listCentros.add((JSONObject)e);
                            });
                            controllerFxml2.setCmbRegion(listRegiones);
                            controllerFxml2.setCmbComuna(listComunas);
                            controllerFxml2.setCmbCentro(listCentros);
                        
                            controllerFxml2.setRegionValue(region.getJSONObject(0));
                            controllerFxml2.setComunaValue(comuna.getJSONObject(0));
                            controllerFxml2.setCentroValue(centro.getJSONObject(0));
                            this.stageController.showContent(panContInfoUsuario, "FormularioPaciente");
                        }
                    }catch(Exception ex){
                        Logger.getLogger(MantenedoresController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
        
    }    
    
    public void setStageController(StageController c){
        this.stageController = c;
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
    //estados de los formularios
    public void estadoVacío(){
        if(modificando > 0 || creando > 0){
            if(tipoFormulario == 1){
                controllerFxml.AnchorParent.setDisable(true);
            }else{
                controllerFxml2.AnchorParent.setDisable(true);
            }
        }
        modificando=0;
        creando=0;
        index=-1;
        btnAgregar.setDisable(false);
        btnModificar.setDisable(true);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
       
        tbvMantUsuario.getSelectionModel().clearSelection();
    }
    public void estadoSeleccionando(){
        modificando=0;
        creando=0;
        btnAgregar.setDisable(false);
        btnModificar.setDisable(false);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }
    public void estadoModificando(){
        modificando=1;
        if(tipoFormulario == 1){
            controllerFxml.AnchorParent.setDisable(false);
        }else{
            controllerFxml2.AnchorParent.setDisable(false);
        }
        btnAgregar.setDisable(true);
        btnModificar.setDisable(true);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
    }
    public void estadoCreando(){
        creando=1;
        btnAgregar.setDisable(true);
        btnModificar.setDisable(true);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        if(tipoFormulario == 1){
            controllerFxml.AnchorParent.setDisable(false);
            controllerFxml.limpiarFormulario();
        }else{
            controllerFxml2.AnchorParent.setDisable(false);
            controllerFxml2.limpiarFormulario();
            
        }
        tbvMantUsuario.getSelectionModel().clearSelection();
    }
    private void updateFilteredData() {
            filteredData.clear();

            for (RowUsuarios r : list) {
                    if (matchesFilter(r)) {
                            filteredData.add(r);
                    }
            }

            // Must re-sort table after items changed
            reapplyTableSortOrder();
    }    
    private boolean matchesFilter(RowUsuarios person) {
            String filterString = txtBuscadorUsuario.getText();
            if (filterString == null || filterString.isEmpty()) {
                    // No filter --> Add all.
                    return true;
            }

            String lowerCaseFilterString = filterString.toLowerCase();

            if (person.getNombre().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                    return true;
            } else if (person.getTipo().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                    return true;
            } else if (person.getRut().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                    return true;
            }
            return false; // Does not match
    }
    private void reapplyTableSortOrder() {
            ArrayList<TableColumn<RowUsuarios, ?>> sortOrder = new ArrayList<>(tbvMantUsuario.getSortOrder());
            tbvMantUsuario.getSortOrder().clear();
            tbvMantUsuario.getSortOrder().addAll(sortOrder);
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