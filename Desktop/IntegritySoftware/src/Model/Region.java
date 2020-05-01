package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Region {
    private Integer id;
    private String nombre;
    private List<Comuna> comunas;

    public Region(Integer id, String nombre,List<Comuna> comunas) {
        this.id = id;
        this.nombre = nombre;
        this.comunas = comunas;
    }
    
    public Region(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    
    public List<Comuna> getComunas() {
        return comunas;
    }

    public void setComunas(List<Comuna> comunas) {
        this.comunas = comunas;
    }
    
}
