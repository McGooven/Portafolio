package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Comuna {
    private Integer id;
    private String name;
    private Region region;
    private List<Direccion> direcciones;

    public Comuna(Integer id, String name, Region region) {
        this.id = id;
        this.name = name;
        this.region = region;
    }

    public Comuna(Integer id, String name, Region region, List<Direccion> direcciones) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.direcciones = direcciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
    
    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }
    
}
