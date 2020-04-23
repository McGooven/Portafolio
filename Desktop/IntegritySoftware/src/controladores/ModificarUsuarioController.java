/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class ModificarUsuarioController implements Initializable {
    
    @FXML TextField txtUsuario, txtCorreo;
    @FXML PasswordField pswContrasenia;
    @FXML ComboBoxBase cmbTipoUsuario;
    @FXML Button btnAceptar, btnCargar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
