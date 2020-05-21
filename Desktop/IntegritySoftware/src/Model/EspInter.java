package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class EspInter {
    private Integer idEspecialidad;
    private String nombre;
    private List<Personal> personals;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   idEspecialidad=").append(idEspecialidad);
        sb.append("   nombre=").append(nombre);
        sb.append("   personals=").append(personals);
        sb.append('}');
        return sb.toString();
    }

    public EspInter() {
    }

    public EspInter(Integer idEspecialidad, String nombre, List<Personal> personals) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
        this.personals = personals;
    }

    public List<Personal> getPersonals() {
        return personals;
    }

    public void setPersonals(List<Personal> personals) {
        this.personals = personals;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
