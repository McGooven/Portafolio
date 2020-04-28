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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Grupo x
 */
public class MantenedoresController implements Initializable {
    StageController stageController;
    
    @FXML private Pane panContMantenedores,panContInfoUsuario;
    @FXML private GridPane grdPContMantUsuario;
    @FXML private TableView<?> tbvMantUsuario;
    @FXML private Button btnGuardar,btnCancelar,btnSalir;
    @FXML private TextField txtBuscadorUsuario;
    @FXML private Label lblUsuario,lblStockInsumo,lblReporteInsumos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setStageController(StageController c){
        this.stageController = c;
    }
}
