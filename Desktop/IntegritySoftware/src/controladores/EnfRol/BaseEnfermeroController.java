package controladores.EnfRol;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import controladores.PeticionJSON;
import controladores.StageController;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.util.calendar.Gregorian;

public class BaseEnfermeroController implements Initializable {
    @FXML 
    private GridPane grdBoxes;
    
    @FXML
    private Label boxId_00;

    @FXML
    private JFXColorPicker colorBox_00;

    @FXML
    private JFXButton boxBtn_00;

    @FXML
    private Label boxId_01;

    @FXML
    private JFXButton boxBtn_01;

    @FXML
    private JFXColorPicker colorBox_01;

    @FXML
    private Label boxId_02;

    @FXML
    private JFXButton boxBtn_02;

    @FXML
    private JFXColorPicker colorBox_02;

    @FXML
    private Label boxId_03;

    @FXML
    private JFXButton boxBtn_03;

    @FXML
    private JFXColorPicker colorBox_03;

    @FXML
    private Label boxId_04;

    @FXML
    private JFXButton boxBtn_04;

    @FXML
    private JFXColorPicker colorBox_04;

    @FXML
    private Label boxId_05;

    @FXML
    private JFXButton boxBtn_05;

    @FXML
    private JFXColorPicker colorBox_05;

    @FXML
    private Label boxId_06;

    @FXML
    private JFXButton boxBtn_06;

    @FXML
    private JFXColorPicker colorBox_06;

    @FXML
    private Label boxId_07;

    @FXML
    private JFXButton boxBtn_07;

    @FXML
    private JFXColorPicker colorBox_07;

    @FXML
    private Label boxId_08;

    @FXML
    private JFXButton boxBtn_08;

    @FXML
    private JFXColorPicker colorBox_08;

    @FXML
    private Label boxId_09;

    @FXML
    private JFXButton boxBtn_09;

    @FXML
    private JFXColorPicker colorBox_09;

    @FXML
    private Label boxId_10;

    @FXML
    private JFXButton boxBtn_10;

    @FXML
    private JFXColorPicker colorBox_10;

    @FXML
    private Label boxId_11;

    @FXML
    private JFXButton boxBtn_11;

    @FXML
    private JFXColorPicker colorBox_11;

    @FXML
    private ListView<?> lstInsumos;

    @FXML
    private JFXDatePicker dtpFecha;

    @FXML
    private JFXTimePicker tpcTime;
    
    @FXML
    private JFXTimePicker tpcTime1;

    @FXML
    private JFXListView<?> lstPersonas;

    @FXML
    private JFXComboBox<JSONObject> cmbTipo;
    
    @FXML
    private JFXTimePicker tmpInicio;
    
    @FXML
    private JFXButton btnBuscarSesion;
    
    @FXML
    private JFXButton btnAgenda;
    
    @FXML
    private JFXButton btnIniSesion;

    @FXML
    private JFXButton btnTerSesion;

    @FXML
    private JFXButton btnSuspSesion;

    @FXML
    private JFXButton btnDayPlan;
    
    @FXML
    private JFXToggleButton tggAgendar;
    
    @FXML
    private Agenda dayAgenda;
    
    @FXML
    private JFXComboBox<Label> cmbBoxes;
    
    StageController stageController;
    List<JSONObject> boxObjects;
    List<Node> gridNodes;
    Timeline boxesState;
    private int boxId;
    private int atencionId;
    
    public String getCmbTipo() {
        return cmbTipo.getValue().getString("nombreTpSn");
    }
    public void setCmbTipo(ObservableList<JSONObject> list) {
        this.cmbTipo.setItems(list);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnIniSesion.setDisable(true);
        btnTerSesion.setDisable(true);
        btnSuspSesion.setDisable(true);
        tmpInicio.set24HourView(true);
        
        
        cmbTipo.setVisibleRowCount(2);
        cmbTipo.setPromptText("Seleccione...");
        cmbTipo.setEditable(true);
        cmbTipo.setConverter(new StringConverter<JSONObject>() {
            @Override
            public String toString(JSONObject object) {
                if (object == null){return null;} 
                
                else {return object.getString("nombreTpSn");}
            }
            @Override
            public JSONObject fromString(String string) {
                return cmbTipo.getItems().stream().filter(ap -> 
                    ap.getString("nombreTpSn").equals(string)).findFirst().orElse(null);
            }
        });
        cmbTipo.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println("tipo seleccionado: " + newval.getString("nombreTpSn")
                    + ", ID: " + ((Integer)newval.getInt("idTpSn")).toString());
        });
        
        btnBuscarSesion.setOnAction((e) ->{
            try {
                SimpleDateFormat dateFormatRaw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateSelected = (dtpFecha.getValue() != null ? dtpFecha.getValue().toString() : "");
                String timeSelected=(tmpInicio.getValue() != null ? tmpInicio.getValue().toString()+":00" : "");
                String fInicio = dateSelected + " "+timeSelected;
                Date dRaw = dateFormatRaw.parse(fInicio);

                dateFormatRaw.applyPattern("dd-MM-yyyy HH:mm:ss");
                fInicio = dateFormatRaw.format(dRaw);
                /*
                Date d = dateFormatRaw.parse(fInicio);

                Calendar startTime = new GregorianCalendar();
                startTime.setTime(d);
                */
                
                JSONObject body = new JSONObject();
                body.put("centroId", this.stageController.idCentro);
                body.put("fechaInicio",fInicio);
                body.put("idTipoSesion",cmbTipo.getValue().getInt("idTpSn"));
                PeticionJSON request = new PeticionJSON(body, "Post", "http://localhost:3000/api/pacPerAvailable");
                request.connect();
                
                if(request.res.isEmpty()){
                    System.out.println("no pacientes o doctores disponibles");
                }else{
                    Stage agendaWindow = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/EnfRol/AgendarSesion.fxml"));
                    AnchorPane root = loader.load();
                    
                    ObservableList<JSONObject> listaObservableBoxes = FXCollections.observableArrayList(boxObjects);
                    
                    AgendarSesionController agendarController = loader.getController();
                    agendarController.setVariables(
                        fInicio, 
                        cmbTipo.getValue(),
                        this.stageController.idCentro,
                        listaObservableBoxes,
                        request.res.getJSONObject(0).getJSONArray("medicos"),
                        request.res.getJSONObject(0).getJSONArray("pacientes")
                    );
                    
                    Scene scene = new Scene(root);

                    agendaWindow.setTitle("Guardar Atención");
                    agendaWindow.setScene(scene);
                    //primaryStage.initStyle(StageStyle.UNDECORATED);
                    agendaWindow.show();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseEnfermeroController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(BaseEnfermeroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        JSONObject body_00 = new JSONObject();
        //body_00.put("", "");
        PeticionJSON request_00 = new PeticionJSON(body_00, "POST", "http://localhost:3000/api/TPS");
        request_00.connect();
        if(!request_00.res.isEmpty()){
            ObservableList<JSONObject> listaTipos = FXCollections.observableArrayList();
            request_00.res.forEach((a)->{
                JSONObject obj = (JSONObject)a;
                listaTipos.add(obj);
            });
            setCmbTipo(listaTipos);
        }
        
        dayAgenda.setSkin(new AgendaDaySkin(dayAgenda));
        gridNodes = grdBoxes.getChildren();
        for (Node node : grdBoxes.getChildren()){
            Pane panel = (Pane)node;
            for( Node e : panel.getChildren() ){
                
                if ( e instanceof JFXColorPicker ){
                    JFXColorPicker clp = (JFXColorPicker) e;
                    clp.setEditable(false);
                }
            }
        }
        
        btnAgenda.setOnAction(e -> {
            try {
                JSONObject body = new JSONObject();
                body.put("centroId", this.stageController.idCentro);
                PeticionJSON request = new PeticionJSON(body, "Post", "http://localhost:3000/api/weekPlan");
                request.connect();
                
                if(request.res.isEmpty()){
                    System.out.println("no hay atenciones");
                }else{
                    Stage agendaWindow = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/EnfRol/Agenda.fxml"));
                    AnchorPane root = loader.load();
                    
                    AgendaController agendaController = loader.getController();
                    agendaController.setStageVariables(request.res);
                    
                    root.getStylesheets().add(getClass().getResource("/CSS/agenda.css").toExternalFo‌​rm());
                    Scene scene = new Scene(root);

                    agendaWindow.setTitle("Bienvenido");
                    agendaWindow.setScene(scene);
                    //primaryStage.initStyle(StageStyle.UNDECORATED);
                    agendaWindow.show();
                }
            } catch (IOException ex) {
                Logger.getLogger(BaseEnfermeroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        boxesState = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            System.out.println(LocalDateTime.now());
            JSONObject body = new JSONObject();
            body.put("idCentro", this.stageController.idCentro);
            
            PeticionJSON request = new PeticionJSON(body, "post", "http://localhost:3000/api/boxes");
            request.connect();
            boxObjects = new ArrayList<JSONObject>();
            
            JSONArray list = request.res.getJSONObject(0).getJSONArray("boxes");
            list.forEach((b) ->{
                JSONObject boxObject = (JSONObject)b;
                System.out.println(boxObject);
                boxObjects.add(boxObject);
                cmbBoxes.getItems().add(new Label(String.valueOf(boxObject.getInt("idBox"))));
            });
            
            RellenarGrid();
        }),new KeyFrame(Duration.seconds(15)));
        
        boxesState.setCycleCount(Animation.INDEFINITE);
        boxesState.play();
        
        btnDayPlan.setOnAction((e)->{
            if(!cmbBoxes.getValue().getText().isEmpty()){
                JSONObject body_01 = new JSONObject();
                int i= Integer.parseInt(cmbBoxes.getValue().getText());
                body_01.put("idBox", i);
                
                PeticionJSON request_01= new PeticionJSON(body_01, "post", "http://localhost:3000/api/filterSesions");
                request_01.connect();
                if(request_01.res.isEmpty()){
                    System.out.println("no hay atenciones");
                    dayAgenda.appointments().clear();
                }else{
                    dayAgenda.refresh();
                    request_01.res.forEach((a) -> {
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        dateFormat.setLenient(false);

                        Date d;
                        try {
                            d = dateFormat.parse(((JSONObject)a).getString("fechaIngreso"));
                            Calendar startTime = new GregorianCalendar();
                            startTime.setTime(d);

                            d = dateFormat.parse(((JSONObject)a).getString("fechaTermino"));
                            Calendar endTime = new GregorianCalendar();
                            endTime.setTime(d);

                            Agenda.AppointmentGroupImpl groupClass;
                            if(((JSONObject)a).getInt("tipo") == 1){
                                groupClass = new Agenda.AppointmentGroupImpl().withStyleClass("group2");
                            }else{
                                groupClass = new Agenda.AppointmentGroupImpl().withStyleClass("group3");
                            }

                            dayAgenda.appointments().add(
                                new Agenda.AppointmentImpl()
                                .withStartTime(startTime)
                                .withEndTime(endTime)
                                .withDescription(String.valueOf(((JSONObject)a).getInt("idAtencion")) + ":" + String.valueOf(((JSONObject)a).getInt("idBox")))
                                .withAppointmentGroup(groupClass)
                            );
                        } catch (ParseException ex) {
                            Logger.getLogger(BaseEnfermeroController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
        });
        
        dayAgenda.selectedAppointments().addListener(new ListChangeListener<Appointment>(){
            public void onChanged(Change <? extends Appointment> c){
                while(c.next()){
                    if (c.wasPermutated()) {
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
                             //permutate
                        }
                    } else if (c.wasUpdated()) {
                             //update item
                    } else {
                        for (Appointment a : c.getRemoved()) {
                        }
                        for (Appointment a : c.getAddedSubList()) {
                            selectedAppointment(a);
                        }
                    }
                }
            }
        });
    }
    public void setStageController(StageController c){
        this.stageController = c;
    }
    
    private void RellenarGrid() {            
        int boxIndex = 0;
        for (Node node : gridNodes){
            JSONObject boxObj = boxObjects.get(boxIndex);
            Pane panel = (Pane)node;
            for( Node e : panel.getChildren() ){
                
                if ( e instanceof JFXButton){
                    JFXButton btn = (JFXButton)e;
                    btn.setText("info");
                }
                
                if ( e instanceof Label ){
                    Label lbl = (Label) e;
                    lbl.setText(String.valueOf(boxObj.getInt("idBox")));
                }
                
                if ( e instanceof JFXColorPicker ){
                    JFXColorPicker clp = (JFXColorPicker) e;
                    if(boxObj.getString("estado").equals("Disponible")){
                        clp.setValue(Color.GREEN);
                    }
                    if(boxObj.getString("estado").equals("Ocupado")){
                        clp.setValue(Color.RED);
                    }
                    if(boxObj.getString("estado").equals("Mantencion")){
                        clp.setValue(Color.ORANGE);
                    }
                    
                }
            }
            
            boxIndex = boxIndex + 1;
        }
    }
    
    private void selectedAppointment(Appointment a) {
        System.out.println(a.getDescription());
        btnIniSesion.setDisable(false);
        
        atencionId = Integer.parseInt(a.getDescription().substring(0,1));
        boxId = Integer.parseInt(a.getDescription().substring(a.getDescription().lastIndexOf(":")+1));
    }
    
    @FXML
    void selectedBox(ActionEvent event) {
        
    }
    @FXML
    void AgendarSesion(ActionEvent event) {
        if(tggAgendar.isSelected() == true){
            btnBuscarSesion.setDisable(false);
        }else{
            btnBuscarSesion.setDisable(true);
        }
    }
    @FXML
    void solicitudEnfermera(ActionEvent event) {
        dayAgenda.refresh();
        JSONObject body_02 = new JSONObject();
        body_02.put("accion",((JFXButton)event.getSource()).getText());
        body_02.put("id", ((JFXButton)event.getSource()).getId());
        body_02.put("idUsuario",this.stageController.idUsuario);
        body_02.put("idBox", this.boxId);
        body_02.put("idAtencion", this.atencionId);
        
        PeticionJSON request_02= new PeticionJSON(body_02, "post", "http://localhost:3000/api/peticionEnfermera");
        request_02.connect();
        
        if(((JFXButton)event.getSource()).getText().equals("Iniciar")){
            btnIniSesion.setDisable(true);
            btnSuspSesion.setDisable(false);
            btnTerSesion.setDisable(false);
        }else{
            btnSuspSesion.setDisable(true);
            btnTerSesion.setDisable(true);
        }
    }
    @FXML
    void RellenarAgendaDay(ActionEvent event) {

    }
}
