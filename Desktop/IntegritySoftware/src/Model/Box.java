package Model;

import java.util.List;

/**
 *
 * @author Diego
 */
public class Box {
    private Integer idBox;
    private String habilitada;
    private String estado;
    private List<Atencion> atencions;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Box{idBox:").append(idBox);
        sb.append(", habilitada:").append(habilitada);
        sb.append(", estado:").append(estado);
        sb.append(", atencions:").append(atencions);
        sb.append(", centroIdCentro:").append(centroIdCentro);
        sb.append('}');
        return sb.toString();
    }

    public Box() {
    }

    public Box(Integer idBox, String habilitada, String estado, List<Atencion> atencions, Centro centroIdCentro) {
        this.idBox = idBox;
        this.habilitada = habilitada;
        this.estado = estado;
        this.atencions = atencions;
        this.centroIdCentro = centroIdCentro;
    }
    
    private Centro centroIdCentro;

    public Integer getIdBox() {
        return idBox;
    }

    public void setIdBox(Integer idBox) {
        this.idBox = idBox;
    }

    public String getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(String habilitada) {
        this.habilitada = habilitada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Atencion> getAtencions() {
        return atencions;
    }

    public void setAtencions(List<Atencion> atencions) {
        this.atencions = atencions;
    }

    public Centro getCentroIdCentro() {
        return centroIdCentro;
    }

    public void setCentroIdCentro(Centro centroIdCentro) {
        this.centroIdCentro = centroIdCentro;
    }
    
}
