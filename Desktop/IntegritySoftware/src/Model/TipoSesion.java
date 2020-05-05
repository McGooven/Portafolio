package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class TipoSesion {
    private String nombreTpSn;
    private Integer idTpSn;
    private List<Atencion> atencions;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   nombreTpSn:").append(nombreTpSn);
        sb.append("   idTpSn:").append(idTpSn);
        sb.append("   atencions:").append(atencions);
        sb.append('}');
        return sb.toString();
    }

    public TipoSesion(String nombreTpSn, Integer idTpSn, List<Atencion> atencions) {
        this.nombreTpSn = nombreTpSn;
        this.idTpSn = idTpSn;
        this.atencions = atencions;
    }

    public TipoSesion() {
    }

    public String getNombreTpSn() {
        return nombreTpSn;
    }

    public void setNombreTpSn(String nombreTpSn) {
        this.nombreTpSn = nombreTpSn;
    }

    public Integer getIdTpSn() {
        return idTpSn;
    }

    public void setIdTpSn(Integer idTpSn) {
        this.idTpSn = idTpSn;
    }

    public List<Atencion> getAtencions() {
        return atencions;
    }

    public void setAtencions(List<Atencion> atencions) {
        this.atencions = atencions;
    }
    
}
