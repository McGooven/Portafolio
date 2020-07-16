package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Region {
    private Integer idRegion;
    private String nombre;
    private List<Comuna> comunas;

    public Region() {
    }

    public Region(Integer idRegion, String nombre, List<Comuna> comunas) {
        this.idRegion = idRegion;
        this.nombre = nombre;
        this.comunas = comunas;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Comuna> getComunas() {
        return comunas;
    }

    public void setComunas(List<Comuna> comunas) {
        this.comunas = comunas;
    }

}
