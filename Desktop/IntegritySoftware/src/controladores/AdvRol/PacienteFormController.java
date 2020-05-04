/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.AdvRol;

import controladores.StageController;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
    
    @FXML private TextField txtCorreo, txtIdFicha, txtEtapa, txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido, txtRut, txtCalle;
    @FXML private DatePicker dmpFechaNacimiento;
    @FXML private Button btnCargar;
    @FXML private ImageView imgFoto;
    @FXML private ComboBox<JSONObject> cmbRegion;
    @FXML private ComboBox<JSONObject> cmbComuna;
    @FXML private ComboBox<JSONObject> cmbCentro;

    @FXML AnchorPane AnchorParent;

    
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
    
    public void setStageController(StageController c){
        this.stageController = c;
    }

    public void setTxtCorreo(TextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public void setTxtIdFicha(TextField txtIdFicha) {
        this.txtIdFicha = txtIdFicha;
    }

    public void setTxtEtapa(TextField txtEtapa) {
        this.txtEtapa = txtEtapa;
    }

    public void setTxtPrimerNombre(TextField txtPrimerNombre) {
        this.txtPrimerNombre = txtPrimerNombre;
    }

    public void setTxtSegundoNombre(TextField txtSegundoNombre) {
        this.txtSegundoNombre = txtSegundoNombre;
    }

    public void setTxtPrimerApellido(TextField txtPrimerApellido) {
        this.txtPrimerApellido = txtPrimerApellido;
    }

    public void setTxtSegundoApellido(TextField txtSegundoApellido) {
        this.txtSegundoApellido = txtSegundoApellido;
    }

    public void setTxtRut(TextField txtRut) {
        this.txtRut = txtRut;
    }

    public void setTxtCalle(TextField txtCalle) {
        this.txtCalle = txtCalle;
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

    public void setCmbBRegion(ComboBox<JSONObject> cmbRegion) {
        this.cmbRegion = cmbRegion;
    }

    public void setCmbBComuna(ComboBox<JSONObject> cmbComuna) {
        this.cmbComuna = cmbComuna;
    }
    
    public void setCmbBCentro(ComboBox<JSONObject> cmbCentro) {
        this.cmbCentro = cmbCentro;
    }

    public StageController getStageController() {
        return stageController;
    }

    public TextField getTxtCorreo() {
        return txtCorreo;
    }

    public TextField getTxtIdFicha() {
        return txtIdFicha;
    }

    public TextField getTxtEtapa() {
        return txtEtapa;
    }

    public TextField getTxtPrimerNombre() {
        return txtPrimerNombre;
    }

    public TextField getTxtSegundoNombre() {
        return txtSegundoNombre;
    }

    public TextField getTxtPrimerApellido() {
        return txtPrimerApellido;
    }

    public TextField getTxtSegundoApellido() {
        return txtSegundoApellido;
    }

    public TextField getTxtRut() {
        return txtRut;
    }

    public TextField getTxtCalle() {
        return txtCalle;
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

    public String getCmbBRegion() {
        return cmbRegion.getValue().getString("nombre");    }

    public String getCmbBComuna() {
        return cmbComuna.getValue().get("nombreComuna").toString();
    }

    public String getCmbBCentro() {
        return cmbCentro.getValue().get("nombreCentro").toString();
    }




    
}
