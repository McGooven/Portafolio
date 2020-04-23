package controladores;

import controladores.AdrRol.CrearBoxController;
import controladores.AdrRol.CrearCentroController;
import controladores.AdrRol.EditarBoxController;
import controladores.AdrRol.HomeAdrRolController;
import controladores.AdvRol.HomeAdvRolController;
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

/**
 *
 * @author Grupo x
 */
public class LoginController implements Initializable {
    
    @FXML TextField TxtUsuario;
    @FXML PasswordField PswContrasenia;
    @FXML Button btnIngresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cuando se obtiene algun nodo de la scene es el momento en que está 
        //"todo" inicializado, por ende solo desde ahí se puede localizar
        //la scene y la stage "actuales" para así poder cerralas(currentStage.close())
        //y crear una nueva.
        btnIngresar.setOnAction((event) -> {
            Node source        = (Node)event.getSource();
            Stage currentStage = (Stage)source.getScene().getWindow();
            System.out.println("cerrando stage: " + currentStage.toString());
            currentStage.close();
            
            Stage newStage = new Stage();
            System.out.println("abriendo stage: " + newStage.toString());
            //System.out.println("if correspondiente: " + PswContrasenia.getText());
            
            if (PswContrasenia.getText().equals("administrador")) {
                //creando los FXMLLoader de cada fxml que representa una scene
                //en el Rol de Aministrador.
                try {
                    FXMLLoader loaderHome         = new FXMLLoader();
                    loaderHome.setLocation(getClass().getResource("/vistas/AdrRol/Home.fxml"));
                    loaderHome.load();
                    
                    FXMLLoader loaderCrearCent    = new FXMLLoader();
                    loaderCrearCent.setLocation(getClass().getResource("/vistas/AdrRol/CrearCentro.fxml"));
                    loaderCrearCent.load();

                    FXMLLoader loaderCrearBox     = new FXMLLoader();
                    loaderCrearBox.setLocation(getClass().getResource("/vistas/AdrRol/CrearBox.fxml"));
                    loaderCrearBox.load();
                    
                    FXMLLoader loaderEditarBox    = new FXMLLoader();
                    loaderEditarBox.setLocation(getClass().getResource("/vistas/AdrRol/EditarBox.fxml"));
                    loaderEditarBox.load();

                    /*FXMLLoader loaderEditarCent = new FXMLLoader();
                    loaderEditarCent.setLocation(getClass().getResource("/vistas/AdrRol/xxxxxxxx.fxml"));
                    loaderEditarCent.load();*/
                    
                    //obteniendo instancias de cada controlador de los fxml.
                    HomeAdrRolController instanciaHome             = loaderHome.getController();
                    CrearCentroController instanciaCrearCent       = loaderCrearCent.getController();
                    CrearBoxController instanciaCrearBox           = loaderCrearBox.getController();
                    EditarBoxController instanciaEditarBox         = loaderEditarBox.getController();
                    /*EditarCentroController instanciaEditarCent   = loaderEditarCent.getController();*/
                    
                    //Crear la instancia StageController y agregarle las scenes del Rol Administrador.
                    StageController stageControl= new StageController(newStage);
                    stageControl.addScene("HomeAdr", loaderHome, new Scene(loaderHome.getRoot()));
                    stageControl.addScene("CrearCent", loaderCrearCent, new Scene(loaderCrearCent.getRoot()));
                    stageControl.addScene("CrearBox", loaderCrearBox, new Scene(loaderCrearBox.getRoot()));
                    stageControl.addScene("EditarBox", loaderEditarBox, new Scene(loaderEditarBox.getRoot()));
                    /*stageControl.addScene("EditarCent", loaderEditarCent, new Scene(loaderEditarCent.getRoot()));*/
                    
                    //Pasar la instancia del StageController al Controller de todas las scenes
                    //involucradas con el Rol Administrativo.
                    instanciaHome.setStageController(stageControl);
                    instanciaCrearCent.setStageController(stageControl);
                    instanciaCrearBox.setStageController(stageControl);
                    instanciaEditarBox.setStageController(stageControl);
                    
                    //Mostrar la scene que quieras.
                    newStage.setTitle("Administrador - Home");
                    stageControl.activate("HomeAdr");
                    
                } catch (Exception ex) {
                    Logger.getLogger(IntegritySoftware.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (PswContrasenia.getText().equals("administrativo")) {

                try {
                    FXMLLoader loaderHome = new FXMLLoader();
                    loaderHome.setLocation(getClass().getResource("/vistas/AdvRol/Home.fxml"));
                    loaderHome.load();
                    
                    HomeAdvRolController instanciaHome = loaderHome.getController();
                    
                    StageController stageControl= new StageController(newStage);
                    stageControl.addScene("HomeAdv", loaderHome, new Scene(loaderHome.getRoot()));
                    
                    instanciaHome.setStageController(stageControl);
                    newStage.setTitle("Administrativo - Home");
                    stageControl.activate("HomeAdv");
                } catch (Exception e) {
                }
            }
            if (PswContrasenia.getText().toString() == "enfermero") {
                //creando los FXMLLoader de cada fxml que representa una scene
                //en el Rol de enfermero.
            }
            if (PswContrasenia.getText().toString() == "medico") {
                //creando los FXMLLoader de cada fxml que representa una scene
                //en el Rol de medico.
            }
            
            //Al ser esta una nueva Stage, no es visible hasta que lo cambies manualmente(show()).
            newStage.show();
        });
    }    
}
