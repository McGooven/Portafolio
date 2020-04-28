package controladores.AdvRol;

import controladores.StageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    @FXML private ComboBox<?> cmbBRegion;
    @FXML private ComboBox<?> cmbBComuna;
    @FXML private ComboBox<?> cmbBCargo;
    @FXML private Button btnFileCertificado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
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

    public void setDtpFechaNacimiento(DatePicker dtpFechaNacimiento) {
        this.dtpFechaNacimiento = dtpFechaNacimiento;
    }

    public void setDtpFechaIngreso(DatePicker dtpFechaIngreso) {
        this.dtpFechaIngreso = dtpFechaIngreso;
    }

    public void setDtpFechaEgreso(DatePicker dtpFechaEgreso) {
        this.dtpFechaEgreso = dtpFechaEgreso;
    }

    public void setCmbBRegion(ComboBox<?> cmbBRegion) {
        this.cmbBRegion = cmbBRegion;
    }

    public void setCmbBComuna(ComboBox<?> cmbBComuna) {
        this.cmbBComuna = cmbBComuna;
    }

    public void setCmbBCargo(ComboBox<?> cmbBCargo) {
        this.cmbBCargo = cmbBCargo;
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

    public DatePicker getDtpFechaNacimiento() {
        return dtpFechaNacimiento;
    }

    public DatePicker getDtpFechaIngreso() {
        return dtpFechaIngreso;
    }

    public DatePicker getDtpFechaEgreso() {
        return dtpFechaEgreso;
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
