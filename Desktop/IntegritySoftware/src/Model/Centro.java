package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Centro {
    private Integer idCentro;
    private String nombreSede;
    private List<Box> boxes;
    private Direccion direccionIdDireccion;
    private List<FichaPaciente> fichaPacientes;
    private List<Personal> personals;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   idCentro=").append(idCentro);
        sb.append("   nombreSede=").append(nombreSede);
        sb.append("   boxes=").append(boxes);
        sb.append("   direccionIdDireccion=").append(direccionIdDireccion);
        sb.append("   fichaPacientes=").append(fichaPacientes);
        sb.append("   personals=").append(personals);
        sb.append('}');
        return sb.toString();
    }


    public Centro() {
    }

    public Centro(Integer idCentro, String nombreSede, List<Box> boxes, Direccion direccionIdDireccion, List<FichaPaciente> fichaPacientes, List<Personal> personals) {
        this.idCentro = idCentro;
        this.nombreSede = nombreSede;
        this.boxes = boxes;
        this.direccionIdDireccion = direccionIdDireccion;
        this.fichaPacientes = fichaPacientes;
        this.personals = personals;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public Direccion getDireccionIdDireccion() {
        return direccionIdDireccion;
    }

    public void setDireccionIdDireccion(Direccion direccionIdDireccion) {
        this.direccionIdDireccion = direccionIdDireccion;
    }

    public List<FichaPaciente> getFichaPacientes() {
        return fichaPacientes;
    }

    public void setFichaPacientes(List<FichaPaciente> fichaPacientes) {
        this.fichaPacientes = fichaPacientes;
    }

    public List<Personal> getPersonals() {
        return personals;
    }

    public void setPersonals(List<Personal> personals) {
        this.personals = personals;
    }
    
    
}
