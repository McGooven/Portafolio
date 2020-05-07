package Model;

/**
 *
 * @author Diego
 */
public class AtenInsu {
    private Integer atencionIdAtencion;
    private Integer cantidad;
    private Integer insumoIdInsumo;
    private Atencion atencionIdAtencion2;
    private Insumo insumoIdInsumo2;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   atencionIdAtencion=").append(atencionIdAtencion);
        sb.append("   cantidad=").append(cantidad);
        sb.append("   insumoIdInsumo=").append(insumoIdInsumo);
        sb.append("   atencionIdAtencion2=").append(atencionIdAtencion2);
        sb.append("   insumoIdInsumo2=").append(insumoIdInsumo2);
        sb.append('}');
        return sb.toString();
    }

    public AtenInsu() {
    }

    public AtenInsu(Integer cantidad, Atencion atencionIdAtencion2, Insumo insumoIdInsumo2) {
        this.atencionIdAtencion = atencionIdAtencion2.getIdAtencion();
        this.cantidad = cantidad;
        this.insumoIdInsumo = insumoIdInsumo2.getIdInsumo();
        this.atencionIdAtencion2 = atencionIdAtencion2;
        this.insumoIdInsumo2 = insumoIdInsumo2;
    }

    public Insumo getInsumoIdInsumo2() {
        return insumoIdInsumo2;
    }

    public void setInsumoIdInsumo2(Insumo insumoIdInsumo2) {
        this.insumoIdInsumo2 = insumoIdInsumo2;
    }

    public Integer getAtencionIdAtencion() {
        return atencionIdAtencion2.getIdAtencion();
    }

    public void setAtencionIdAtencion(Integer atencionIdAtencion) {
        this.atencionIdAtencion = atencionIdAtencion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getInsumoIdInsumo() {
        return insumoIdInsumo2.getIdInsumo();
    }

    public void setInsumoIdInsumo(Integer insumoIdInsumo) {
        this.insumoIdInsumo = insumoIdInsumo;
    }

    public Atencion getAtencionIdAtencion2() {
        return atencionIdAtencion2;
    }

    public void setAtencionIdAtencion2(Atencion atencionIdAtencion2) {
        this.atencionIdAtencion2 = atencionIdAtencion2;
    }
    
}
