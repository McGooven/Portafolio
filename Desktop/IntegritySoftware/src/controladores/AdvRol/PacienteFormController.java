package controladores.AdvRol;

import controladores.StageController;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.json.JSONObject;
/**
 * FXML Controller class
 *
 * @author rober,Diego
 */
public class PacienteFormController implements Initializable {
    StageController stageController;
    
    @FXML private TextField txtCorreo, txtidFicha, txtEtapa, txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido, txtRut, txtCalle;
    @FXML private DatePicker dmpFechaNacimiento;
    @FXML private Button btnCargar;
    @FXML private ImageView imgFoto;
    @FXML private ComboBox<JSONObject> cmbRegion;
    @FXML private ComboBox<JSONObject> cmbComuna;
    @FXML private ComboBox<JSONObject> cmbCentro;

    @FXML public AnchorPane AnchorParent;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cmbRegion.setVisibleRowCount(4);
        cmbRegion.setPromptText("Seleccione Región...");
        cmbRegion.setEditable(true);
        cmbRegion.setConverter(new StringConverter<JSONObject>() {
            @Override
            public String toString(JSONObject object) {
                if (object == null){return null;} 
                
                else {return object.getString("nombre");}
            }
            @Override
            public JSONObject fromString(String string) {
                return cmbRegion.getItems().stream().filter(ap -> 
                    ap.getString(string).equals(string)).findFirst().orElse(null);
            }
        }); 
        
        cmbComuna.setVisibleRowCount(4);
        cmbComuna.setPromptText("Seleccione Comuna...");
        cmbComuna.setEditable(true);
        cmbComuna.setConverter(new StringConverter<JSONObject>() {
            @Override
            public String toString(JSONObject object) {
                if(object== null){return null;}
                
                else{return object.getString("nombreComuna");}
            }
            @Override
            public JSONObject fromString(String string) {
                return cmbComuna.getItems().stream().filter(ap -> 
                    ap.getString(string).equals(string)).findFirst().orElse(null);
            }
        });             
    
        cmbCentro.setVisibleRowCount(4);
        cmbCentro.setPromptText("Seleccione Sede...");
        cmbCentro.setEditable(true);
        cmbCentro.setConverter(new StringConverter<JSONObject>() {
            @Override
            public String toString(JSONObject object) {
                if (object == null){return null;} 
                
                else {return object.getString("nombreSede");}
            }
            @Override
            public JSONObject fromString(String string) {
                return cmbCentro.getItems().stream().filter(ap -> 
                    ap.getString(string).equals(string)).findFirst().orElse(null);
            }
        }); 
        cmbRegion.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println("region seleccionada: " + newval.getString("nombre")
                    + ". ID: " + ((Integer)newval.getInt("idRegion")).toString());
        });
        cmbComuna.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println("comuna seleccionada: " + newval.getString("nombreComuna")
                    + ". ID: " + ((Integer)newval.getNumber("idComuna")).toString());
        });
        cmbCentro.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println("Sede seleccionada: " + newval.getString("nombreSede")
                    + ". ID: " + ((Integer)newval.getNumber("idCentro")).toString());
        });


        AnchorParent.setDisable(true);

    }    
    
      public void limpiarFormulario(){
        this.txtidFicha.clear();
        this.txtPrimerNombre.clear();
        this.txtSegundoNombre.clear();
        this.txtPrimerApellido.clear();
        this.txtSegundoApellido.clear();
        this.txtRut.clear();
        this.txtCorreo.clear();
        this.txtCalle.clear();
        this.dmpFechaNacimiento.setValue(null);
        this.cmbRegion.getSelectionModel().clearSelection();
        this.cmbComuna.getSelectionModel().clearSelection();
        this.cmbCentro.getSelectionModel().clearSelection();
    }
    public void setStageController(StageController c){
        this.stageController = c;
    }

    public void setTxtCorreo(String txtCorreo) {
        this.txtCorreo.setText(txtCorreo);
    }

    public void setTxtIdFicha(String txtidFicha) {
        this.txtidFicha.setText(txtidFicha);
    }

    public void setTxtEtapa(String txtEtapa) {
        this.txtEtapa.setText(txtEtapa);
    }

    public void setTxtPrimerNombre(String txtPrimerNombre) {
        this.txtPrimerNombre.setText(txtPrimerNombre);
    }

    public void setTxtSegundoNombre(String txtSegundoNombre) {
        this.txtSegundoNombre.setText(txtSegundoNombre);
    }

    public void setTxtPrimerApellido(String txtPrimerApellido) {
        this.txtPrimerApellido.setText(txtPrimerApellido);
    }

    public void setTxtSegundoApellido(String txtSegundoApellido) {
        this.txtSegundoApellido.setText(txtSegundoApellido);
    }

    public void setTxtRut(String txtRut) {
        this.txtRut.setText(txtRut);
    }
    public void setTxtCalle(String txtCalle) {
        this.txtCalle.setText(txtCalle);
    }

    public void setDmpFechaNacimiento(LocalDate fecha) {
        this.dmpFechaNacimiento.setValue(fecha);
    }

    public void setBtnCargar(Button btnCargar) {
        this.btnCargar = btnCargar;
    }

    public void setImgFoto(ImageView imgFoto) {
        this.imgFoto = imgFoto;
    }

    public void setCmbRegion(ComboBox<JSONObject> cmbRegion) {
        this.cmbRegion = cmbRegion;
    }
    
      public void setCmbRegion(ObservableList<JSONObject> list) {
        this.cmbRegion.setItems(list);  
    }
      
    public void setRegionValue(JSONObject obj){
        this.cmbRegion.setValue(obj);
    }

    public void setCmbComuna(ComboBox<JSONObject> cmbComuna) {
        this.cmbComuna = cmbComuna;
    }
    
    public void setCmbComuna(ObservableList<JSONObject> list) {
        this.cmbComuna.setItems(list); 
    }
    
    public void setComunaValue(JSONObject obj){
        this.cmbComuna.setValue(obj);
    }
    
    public void setCmbCentro(ComboBox<JSONObject> cmbCentro) {
        this.cmbCentro = cmbCentro;
    }
    public void setCmbCentro(ObservableList<JSONObject> list) {
        this.cmbCentro.setItems(list); 
    }
    
    public void setCentroValue(JSONObject obj){
        this.cmbCentro.setValue(obj);
    }    

    
    public StageController getStageController() {
        return stageController;
    }

    public String getTxtCorreo() {
        return txtCorreo.getText();
    }

    public String getTxtIdFicha() {
        return txtidFicha.getText();
    }

    public String getTxtEtapa() {
        return txtEtapa.getText();
    }

    public String getTxtPrimerNombre() {
        return txtPrimerNombre.getText();
    }

    public String getTxtSegundoNombre() {
        return txtSegundoNombre.getText();
    }

    public String getTxtPrimerApellido() {
        return txtPrimerApellido.getText();
    }

    public String getTxtSegundoApellido() {
        return txtSegundoApellido.getText();
    }

    public String getTxtRut() {
        return txtRut.getText();
    }

    public String getTxtCalle() {
        return txtCalle.getText();
    }

    public String getDmpFechaNacimiento() {
        return dmpFechaNacimiento.getValue().toString();
    }

    public Button getBtnCargar() {
        return btnCargar;
    }

    public ImageView getImgFoto() {
        return imgFoto;
    }

    public JSONObject getCmbRegion() {
        return cmbRegion.getValue();    
    }

    public JSONObject getCmbComuna() {
        return cmbComuna.getValue();
    }

    public JSONObject getCmbCentro() {
        return cmbCentro.getValue();
    }
}