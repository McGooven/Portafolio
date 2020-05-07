package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Insumo {
    private Integer idInsumo;
    private Integer stock;
    private String nombre;
    private String unidadMedida;
    private String habilitado;
    private List<AtenInsu> atenInsus;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   idInsumo:").append(idInsumo);
        sb.append("   stock:").append(stock);
        sb.append("   nombre:").append(nombre);
        sb.append("   unidadMedida:").append(unidadMedida);
        sb.append("   habilitado:").append(habilitado);
        sb.append("   atenInsus:").append(atenInsus);
        sb.append('}');
        return sb.toString();
    }

    public Insumo() {
    }

    public Insumo(Integer idInsumo, Integer stock, String nombre, String unidadMedida, String habilitado, List<AtenInsu> atenInsus) {
        this.idInsumo = idInsumo;
        this.stock = stock;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.habilitado = habilitado;
        this.atenInsus = atenInsus;
    }

    public List<AtenInsu> getAtenInsus() {
        return atenInsus;
    }

    public void setAtenInsus(List<AtenInsu> atenInsus) {
        this.atenInsus = atenInsus;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }
    
}
