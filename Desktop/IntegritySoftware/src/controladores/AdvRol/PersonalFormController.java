package controladores.AdvRol;

import controladores.StageController;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Diego
 */
public class PersonalFormController implements Initializable {
    StageController stageController;

    @FXML private TextField txtId,txtPNombre,txtSNombre,txtPApellido,txtSApellido,txtRut,txtEmail;
    @FXML private TextField txtDireccion,txtTitulo,txtCasaEstudio,txtFilePath;
    @FXML private ImageView imgFoto;
    @FXML private DatePicker dtpFechaNacimiento,dtpFechaIngreso,dtpFechaEgreso;
    @FXML private ComboBox<JSONObject> cmbBRegion;
    @FXML private ComboBox<JSONObject> cmbBComuna;
    @FXML private ComboBox<JSONObject> cmbBCargo;
    @FXML private Button btnFileCertificado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbBRegion.setVisibleRowCount(4);
        cmbBRegion.setPromptText("Seleccione Región...");
        cmbBRegion.setEditable(true);
        cmbBRegion.setConverter(new StringConverter<JSONObject>() {
            @Override
            public String toString(JSONObject object) {
                if (object == null){return null;} 
                
                else {return object.getString("nombre");}
            }
            @Override
            public JSONObject fromString(String string) {
                return cmbBRegion.getItems().stream().filter(ap -> 
                    ap.getString(string).equals(string)).findFirst().orElse(null);
            }
        }); 
        
        cmbBComuna.setVisibleRowCount(4);
        cmbBComuna.setPromptText("Seleccione Comuna...");
        cmbBComuna.setEditable(true);
        cmbBComuna.setConverter(new StringConverter<JSONObject>() {
            @Override
            public String toString(JSONObject object) {
                if(object== null){return null;}
                
                else{return object.getString("nombreComuna");}
            }
            @Override
            public JSONObject fromString(String string) {
                return cmbBComuna.getItems().stream().filter(ap -> 
                    ap.getString(string).equals(string)).findFirst().orElse(null);
            }
        });             
        
        cmbBCargo.setVisibleRowCount(4);
        cmbBCargo.setPromptText("Seleccione Cargo...");
        cmbBCargo.setEditable(true);
        cmbBCargo.setConverter(new StringConverter<JSONObject>() {
            @Override
            public String toString(JSONObject object) {
                if (object == null) {return null;}
                
                else{return object.getString("nombre");}                
            }
            @Override
            public JSONObject fromString(String string) {
                return cmbBCargo.getItems().stream().filter(ap -> 
                    ap.getString(string).equals(string)).findFirst().orElse(null);
            }
        });

        
        //eventos en los comboboxes
        cmbBRegion.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println("region seleccionada: " + newval.getString("nombre")
                    + ". ID: " + ((Integer)newval.getInt("idRegion")).toString());
        });
        cmbBComuna.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println("comuna seleccionada: " + newval.getString("nombreComuna")
                    + ". ID: " + ((Integer)newval.getNumber("idComuna")).toString());
        });
        cmbBCargo.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                System.out.println("cargo seleccionado: " + newval.getString("nombre")
                    + ". ID: " + ((Integer)newval.getNumber("idEspecialidad")).toString());
        });        
    }    
    
    public void setStageController(StageController c){
        this.stageController = c;
    }
    
    public void setTxtId(String txtId) {
        this.txtId.setText(txtId);
    }

    public void setTxtPNombre(String txtPNombre) {
        this.txtPNombre.setText(txtPNombre);
    }

    public void setTxtSNombre(String txtSNombre) {
        this.txtSNombre.setText(txtSNombre);
    }

    public void setTxtPApellido(String txtPApellido) {
        this.txtPApellido.setText(txtPApellido);
    }

    public void setTxtSApellido(String txtSApellido) {
        this.txtSApellido.setText(txtSApellido);
    }

    public void setTxtRut(String txtRut) {
        this.txtRut.setText(txtRut);
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail.setText(txtEmail);
    }

    public void setTxtDireccion(String txtDireccion) {
        this.txtDireccion.setText(txtDireccion);
    }

    public void setTxtTitulo(String txtTitulo) {
        this.txtTitulo.setText(txtTitulo);
    }

    public void setTxtCasaEstudio(String txtCasaEstudio) {
        this.txtCasaEstudio.setText(txtCasaEstudio);
    }

    public void setTxtFilePath(String txtFilePath) {
        this.txtFilePath.setText(txtFilePath);
    }

    public void setImgFoto(ImageView imgFoto) {
        this.imgFoto = imgFoto;
    }

    public void setDtpFechaNacimiento(LocalDate fecha) {
        this.dtpFechaNacimiento.setValue(fecha);
    }

    public void setDtpFechaIngreso(LocalDate fecha) {
        this.dtpFechaIngreso.setValue(fecha);
    }

    public void setDtpFechaEgreso(LocalDate fecha) {
        this.dtpFechaEgreso.setValue(fecha);
    }

    public void setCmbBRegion(ObservableList<JSONObject> list) {
        this.cmbBRegion.setItems(list);
        this.cmbBRegion.setValue((JSONObject)list.get(0));
        
    }

    public void setCmbBComuna(ObservableList<JSONObject> list) {
        this.cmbBComuna.setItems(list); 
        this.cmbBComuna.setValue((JSONObject)list.get(0));
    }

    public void setCmbBCargo(ObservableList<JSONObject> list) {
        this.cmbBCargo.setItems(list); 
        this.cmbBCargo.setValue(((JSONObject)list.get(0)));
    }

    public void setBtnFileCertificado(Button btnFileCertificado) {
        this.btnFileCertificado = btnFileCertificado;
    }
    
    public String getTxtId() {
        return txtId.getText();
    }

    public String getTxtPNombre() {
        return txtPNombre.getText();
    }

    public String getTxtSNombre() {
        return txtSNombre.getText();
    }

    public String getTxtPApellido() {
        return txtPApellido.getText();
    }

    public String getTxtSApellido() {
        return txtSApellido.getText();
    }

    public String getTxtRut() {
        return txtRut.getText();
    }

    public String getTxtEmail() {
        return txtEmail.getText();
    }

    public String getTxtDireccion() {
        return txtDireccion.getText();
    }

    public String getTxtTitulo() {
        return txtTitulo.getText();
    }

    public String getTxtCasaEstudio() {
        return txtCasaEstudio.getText();
    }

    public String getTxtFilePath() {
        return txtFilePath.getText();
    }

    public ImageView getImgFoto() {
        return imgFoto;
    }

    public LocalDate getDtpFechaNacimiento() {
        return dtpFechaNacimiento.getValue();
    }

    public LocalDate getDtpFechaIngreso() {
        return dtpFechaIngreso.getValue();
    }

    public LocalDate getDtpFechaEgreso() {
        return dtpFechaEgreso.getValue();
    }

    public ComboBox<?> getCmbBRegion() {
        return cmbBRegion;
    }

    public ComboBox<?> getCmbBComuna() {
        return cmbBComuna;
    }

    public ComboBox<?> getCmbBCargo() {
        return cmbBCargo;
    }

    public Button getBtnFileCertificado() {
        return btnFileCertificado;
    }
}
