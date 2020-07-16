package controladores.EnfRol;

import controladores.EnfRol.BaseEnfermeroController;
import controladores.PeticionJSON;
import controladores.StageController;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import org.json.JSONArray;
import org.json.JSONObject;

public class AgendaController implements Initializable {
    private JSONArray atenciones;
       
    @FXML
    private Agenda agenda;
    GregorianCalendar calendar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setStageVariables(JSONArray b){
        this.atenciones = b;
        
        agenda.setSkin(new AgendaWeekSkin(agenda));
        
        agenda.refresh();
        atenciones.forEach((a) ->{
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dateFormat.setLenient(false);

            Date d;
            try {
                d = dateFormat.parse(((JSONObject)a).getString("fechaIngreso"));
                Calendar startTime = new GregorianCalendar();
                startTime.setTime(d);

                d = dateFormat.parse(((JSONObject)a).getString("fechaTermino"));
                Calendar endTime = new GregorianCalendar();
                endTime.setTime(d);

                Agenda.AppointmentGroupImpl groupClass;
                if(((JSONObject)a).getInt("tipo") == 1){
                    groupClass = new Agenda.AppointmentGroupImpl().withStyleClass("group2");
                }else{
                    groupClass = new Agenda.AppointmentGroupImpl().withStyleClass("group3");
                }

                agenda.appointments().add(
                    new Agenda.AppointmentImpl()
                    .withStartTime(startTime)
                    .withEndTime(endTime)
                    .withDescription(String.valueOf(((JSONObject)a).getInt("idAtencion")) + ":" + String.valueOf(((JSONObject)a).getInt("idBox")))
                    .withAppointmentGroup(groupClass)
                    .withSummary("Id de Box: "+String.valueOf(((JSONObject)a).getInt("idBox")))
                );
            } catch (ParseException ex) {
                Logger.getLogger(BaseEnfermeroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
