package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Direccion {
    private Integer idDireccion;
    private String direccion;
    private Comuna comunaComuna;
    private List<Centro> centros;
    private List<Personal> personals;
    private List<FichaPaciente> fichaPacientes;

    public Direccion() {
    }

    public Direccion(Integer idDireccion, String direccion, Comuna comunaComuna, List<Centro> centros, List<Personal> personals, List<FichaPaciente> fichaPacientes) {
        this.idDireccion = idDireccion;
        this.direccion = direccion;
        this.comunaComuna = comunaComuna;
        this.centros = centros;
        this.personals = personals;
        this.fichaPacientes = fichaPacientes;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Comuna getComunaComuna() {
        return comunaComuna;
    }

    public void setComunaComuna(Comuna comunaComuna) {
        this.comunaComuna = comunaComuna;
    }

    public List<Centro> getCentros() {
        return centros;
    }

    public void setCentros(List<Centro> centros) {
        this.centros = centros;
    }

    public List<Personal> getPersonals() {
        return personals;
    }

    public void setPersonals(List<Personal> personals) {
        this.personals = personals;
    }

    public List<FichaPaciente> getFichaPacientes() {
        return fichaPacientes;
    }

    public void setFichaPacientes(List<FichaPaciente> fichaPacientes) {
        this.fichaPacientes = fichaPacientes;
    }

}
