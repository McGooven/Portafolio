package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Direccion {
    private Integer id;
    private String nombre;
    private Comuna comuna;
    private List<Centro> centros;
    private List<Personal> personales;
    private List<FichaPaciente>pacientes;
    
    
    public Direccion(Integer id, String nombre, Comuna comuna) {
        this.id = id;
        this.nombre = nombre;
        this.comuna = comuna;
    }

    public Direccion(Integer id, String nombre, Comuna comuna, List<Centro> centros) {
        this.id = id;
        this.nombre = nombre;
        this.comuna = comuna;
        this.centros = centros;
    }

    public Direccion(Integer id,List<FichaPaciente> pacientes, String nombre, Comuna comuna) {
        this.id = id;
        this.nombre = nombre;
        this.comuna = comuna;
        this.pacientes = pacientes;
    }

    public Direccion( List<Personal> personales,Integer id, String nombre, Comuna comuna) {
        this.id = id;
        this.nombre = nombre;
        this.comuna = comuna;
        this.personales = personales;
    }

    public List<Centro> getCentros() {
        return centros;
    }

    public void setCentros(List<Centro> centros) {
        this.centros = centros;
    }

    public List<Personal> getPersonales() {
        return personales;
    }

    public void setPersonales(List<Personal> personales) {
        this.personales = personales;
    }

    public List<FichaPaciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<FichaPaciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }
    
}
