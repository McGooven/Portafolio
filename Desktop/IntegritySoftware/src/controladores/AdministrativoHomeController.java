/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class AdministrativoHomeController implements Initializable {
    @FXML Button btnCrearUsuario, btnModificarUsuario, btnReportarInsumo, btnModificarInsumo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCrearUsuario.setOnAction(event) -> {
        
        
    }      
    
}
}