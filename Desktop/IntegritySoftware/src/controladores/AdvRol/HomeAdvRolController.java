/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.AdvRol;

import controladores.StageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Grupo x
 */
public class HomeAdvRolController implements Initializable {
    StageController stageController;
    @FXML
    private Button btnCrearUsuario,btnReportarInsumo,btnModificarUsuario,btnModificarInsumo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setStageController(StageController c){
        this.stageController = c;
    }
}
