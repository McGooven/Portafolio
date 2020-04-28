/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.AdvRol;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author rober
 */
public class PacienteFormController implements Initializable {

    /**
     * Initializes the controller class.
     * 
     */
    @FXML  TextField txtCorreo, txtIdFicha, txtEtapa, txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido, txtRut, txtCalle;
    @FXML  ComboBox cmbCentro, cmbRegion, cmbComuna;
    @FXML  DatePicker dmpFechaNacimiento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

    }    
    
}
