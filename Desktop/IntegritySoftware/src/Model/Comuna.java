package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Comuna {
    private Integer idComuna;
    private String nombreComuna;
    private Region regionIdRegion;
        private List<Direccion> direccions;

    public Comuna() {
    }

    public Comuna(Integer idComuna, String nombreComuna, Region regionIdRegion, List<Direccion> direccions) {
        this.idComuna = idComuna;
        this.nombreComuna = nombreComuna;
        this.regionIdRegion = regionIdRegion;
        this.direccions = direccions;
    }

    public Integer getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Integer idComuna) {
        this.idComuna = idComuna;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }

    public Region getRegionIdRegion() {
        return regionIdRegion;
    }

    public void setRegionIdRegion(Region regionIdRegion) {
        this.regionIdRegion = regionIdRegion;
    }

    public List<Direccion> getDireccions() {
        return direccions;
    }

    public void setDireccions(List<Direccion> direccions) {
        this.direccions = direccions;
    }

    
}
