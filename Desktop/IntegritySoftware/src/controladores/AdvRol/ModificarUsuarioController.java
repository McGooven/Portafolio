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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Grupo x
 */
public class ModificarUsuarioController implements Initializable {

    @FXML
    private Button btnAceptar,btnCargar;
    @FXML
    private TextField txtUsuario,txtCorreo;
    @FXML
    private ComboBox<?> cmbTipoUsuario;
    @FXML
    private PasswordField pswContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
