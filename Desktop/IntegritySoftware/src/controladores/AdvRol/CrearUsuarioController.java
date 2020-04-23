package controladores.AdvRol;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Grupo x
 */
public class CrearUsuarioController implements Initializable {

    @FXML
    private Button btnAceptar;
    @FXML
    private TextField txtUsuario,txtCorreo;
    @FXML
    private ComboBox<?> cmbTipoUsuario;
    @FXML
    private PasswordField pswContrasenia;
    @FXML
    private ImageView imgLogo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
