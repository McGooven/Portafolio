package controladores;

import controladores.AdrRol.SedBoxManteController;
import controladores.AdvRol.MantenedoresController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Grupo x
 */
public class LoginController implements Initializable {
    
    @FXML TextField TxtUsuario;
    @FXML PasswordField PswContrasenia;
    @FXML Button btnIngresar;
    PeticionJSON jsonReader;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cuando se obtiene algun nodo de la scene es el momento en que está 
        //"todo" inicializado, por ende solo desde ahí se puede localizar
        //la scene y la stage "actuales" para así poder cerralas(currentStage.close())
        //y crear una nueva.
        btnIngresar.setOnAction((event) -> {
            Node source        = (Node)event.getSource();
            Stage currentStage = (Stage)source.getScene().getWindow();
            currentStage.close();
            
            Stage newStage = new Stage();
            
            JSONObject my= new JSONObject();
            my.put("correo", TxtUsuario.getText());
            my.put("password", PswContrasenia.getText());
            PeticionJSON request = new PeticionJSON(my,"post","http://localhost:3000/api/usuario/log/");
            request.connect();
            boolean executed=true;
            
            if (PswContrasenia.getText().equals("admin")) {
                //creando los FXMLLoader de cada fxml que representa una scene
                //en el Rol de Aministrador.
                try {
                    FXMLLoader loaderMantenedor = new FXMLLoader();
                    loaderMantenedor.setLocation(getClass().getResource("/vistas/AdrRol/SedBoxMante.fxml"));
                    loaderMantenedor.load();
                    
                    //obteniendo instancias de cada controlador de los fxml.
                    SedBoxManteController instanciaMantenedor = loaderMantenedor.getController();
                    
                    //Crear la instancia StageController y agregarle las scenes del Rol Administrador.
                    Scene scena=new Scene(loaderMantenedor.getRoot());
                    StageController stageControl= new StageController(newStage);
                    stageControl.addScene("MantenedorSedeBox", loaderMantenedor, scena);

                    
                    //Pasar la instancia del StageController al Controller de todas las scenes
                    //involucradas con el Rol Administrativo.
                    instanciaMantenedor.setStageController(stageControl);
                    scena.getStylesheets().add("/CSS/AdministradorRol.css");

                    //Mostrar la scene que quieras.
                    newStage.setTitle("Mandenedor de sedes");
                    stageControl.activateScene("MantenedorSedeBox");
                    
                } catch (Exception ex) {
                    Logger.getLogger(IntegritySoftware.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (request.comparePrimitives((JSONObject)request.res.get(0), "tipoUsuario", new Integer(2))) {

                try {
                    FXMLLoader loaderHome = new FXMLLoader();
                    loaderHome.setLocation(getClass().getResource("/vistas/AdvRol/Mantenedores.fxml"));
                    loaderHome.load();
                    
                    MantenedoresController instanciaMantenedores = loaderHome.getController();
                    
                    StageController stageControl= new StageController(newStage);
                    stageControl.addScene("HomeAdv", loaderHome, new Scene(loaderHome.getRoot()));
                    
                    instanciaMantenedores.setStageController(stageControl);
                    newStage.setTitle("Administrativo - Home");
                    stageControl.activateScene("HomeAdv");
                } catch (Exception e) {
                }
            }
            else if (request.comparePrimitives((JSONObject)request.res.get(0), "tipoUsuario", new Integer(3))) {
                //creando los FXMLLoader de cada fxml que representa una scene
                //en el Rol de enfermero.
            }
            else if (request.comparePrimitives((JSONObject)request.res.get(0), "tipoUsuario", new Integer(4))) {
                //creando los FXMLLoader de cada fxml que representa una scene
                //en el Rol de medico.
            }
            else{
                executed=false;
            }
            
            //Mostrando la stage creada.
            if (executed) {
                newStage.show();
            }else{
                System.out.println("No se encontro su usario");
                newStage.close();
            }

        });
    }    
}
