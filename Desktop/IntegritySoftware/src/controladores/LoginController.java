package controladores;

import controladores.AdrRol.SedBoxManteController;
import controladores.AdvRol.MantenedoresController;
import controladores.EnfRol.BaseEnfermeroController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML Label lblErrorLog;
    PeticionJSON jsonReader;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblErrorLog.setVisible(false);
        
        TxtUsuario.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue)
                {
                    TxtUsuario.getStyleClass().remove("wrongCredentialBox");
                    lblErrorLog.setVisible(false);
                    //System.out.println("Textfield on focus");
                }
                else
                {
                    //System.out.println("Textfield out focus");
                }
            }
        });
        
        PswContrasenia.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue)
                {
                    PswContrasenia.getStyleClass().remove("wrongCredentialBox");
                    lblErrorLog.setVisible(false);
                    //System.out.println("Textfield on focus");
                }
                else
                {
                    //System.out.println("Textfield out focus");
                }
            }
        });
        
        //cuando se obtiene algun nodo de la scene es el momento en que está 
        //"todo" inicializado, por ende solo desde ahí se puede localizar
        //la scene y la stage "actuales" para así poder cerralas(currentStage.close())
        //y crear una nueva.
        btnIngresar.setOnAction((event) -> {
            Node source        = (Node)event.getSource();
            Stage currentStage = (Stage)source.getScene().getWindow();
            
            Stage newStage = new Stage();
            
            JSONObject my= new JSONObject();
            my.put("correo", TxtUsuario.getText());
            my.put("password", PswContrasenia.getText());
            PeticionJSON request = new PeticionJSON(my,"post","http://localhost:3000/api/usuario/log/");
            request.connect();
            boolean executed=true;
            
            try {
                if (request.comparePrimitives((JSONObject)request.res.get(0), "permisos", new Integer(1))) {
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
                        stageControl.tipoUsuario = request.res.getJSONObject(0).getInt("permisos");
                        stageControl.idUsuario = request.res.getJSONObject(0).getInt("idUsuario");
                        stageControl.idCentro = request.res.getJSONObject(0).getInt("idCentro");


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
                else if (request.comparePrimitives((JSONObject)request.res.get(0), "permisos", new Integer(2))) {
                    try {
                        FXMLLoader loaderHome = new FXMLLoader();
                        loaderHome.setLocation(getClass().getResource("/vistas/AdvRol/Mantenedores.fxml"));
                        loaderHome.load();

                        MantenedoresController instanciaMantenedores = loaderHome.getController();

                        StageController stageControl= new StageController(newStage);
                        stageControl.addScene("HomeAdv", loaderHome, new Scene(loaderHome.getRoot()));
                        stageControl.tipoUsuario = request.res.getJSONObject(0).getInt("permisos");
                        stageControl.idUsuario = request.res.getJSONObject(0).getInt("idUsuario");
                        stageControl.idCentro = request.res.getJSONObject(0).getInt("idCentro");

                        instanciaMantenedores.setStageController(stageControl);
                        newStage.setTitle("Administrativo - Home");
                        stageControl.activateScene("HomeAdv");

                    } catch (Exception e) {
                        Logger.getLogger(IntegritySoftware.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                else if (request.comparePrimitives((JSONObject)request.res.get(0), "permisos", new Integer(3))) {
                    //creando los FXMLLoader de cada fxml que representa una scene
                    //en el Rol de enfermero.
                    try {
                        FXMLLoader loaderHome = new FXMLLoader();
                        loaderHome.setLocation(getClass().getResource("/vistas/EnfRol/BaseEnfermero.fxml"));
                        loaderHome.load();

                        BaseEnfermeroController instanciaBaseEnfermero =loaderHome.getController();

                        StageController stageControl = new StageController(newStage);
                        stageControl.addScene("homeEnfermero", loaderHome, new Scene(loaderHome.getRoot()));
                        stageControl.tipoUsuario = request.res.getJSONObject(0).getInt("permisos");
                        stageControl.idUsuario = request.res.getJSONObject(0).getInt("idUsuario");
                        stageControl.idCentro = request.res.getJSONObject(0).getInt("idCentro");

                        instanciaBaseEnfermero.setStageController(stageControl);
                        newStage.setTitle("Edudown - Enfermeros");
                        stageControl.activateScene("homeEnfermero");
                    } catch (Exception e) {
                        Logger.getLogger(IntegritySoftware.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                else if (request.comparePrimitives((JSONObject)request.res.get(0), "permisos", new Integer(4))) {
                    //creando los FXMLLoader de cada fxml que representa una scene
                    //en el Rol de medico.
                }
                else{
                    System.out.println("Permisos fuera de rango revisar BD.");
                    executed=false;
                }
            } catch (Exception e) {
                executed = false;
                lblErrorLog.setVisible(true);
                TxtUsuario.clear();
                PswContrasenia.clear();
                TxtUsuario.getStyleClass().add("wrongCredentialBox");
                PswContrasenia.getStyleClass().add("wrongCredentialBox");
            }
            
            //Mostrando la stage creada.
            if (executed) {
                currentStage.close();
                newStage.show();
            }

        });
    }    
}
