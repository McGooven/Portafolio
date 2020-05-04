package Model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Diego
 */
public class Atencion {
    private Date fechaIngreso;
    private Date fechaTermino;
    private Integer idAtencion;
    private Integer situacion;
    private Box boxIdBox;
    private TipoSesion tipoSesionIdTpSn;
    private List<AtenInsu> atenInsus;
    private List<FichaPaciente> fichaPacientes;
    private List<PersoAten> persoAtens;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   fechaIngreso:").append(fechaIngreso);
        sb.append("   fechaTermino:").append(fechaTermino);
        sb.append("   idAtencion:").append(idAtencion);
        sb.append("   situacion:").append(situacion);
        sb.append("   boxIdBox:").append(boxIdBox);
        sb.append("   tipoSesionIdTpSn:").append(tipoSesionIdTpSn);
        sb.append("   atenInsus:").append(atenInsus);
        sb.append("   fichaPacientes:").append(fichaPacientes);
        sb.append("   persoAtens:").append(persoAtens);
        sb.append("},");
        return sb.toString();
    }

    public Atencion(Date fechaIngreso, Date fechaTermino, Integer idAtencion, Integer situacion, Box boxIdBox, TipoSesion tipoSesionIdTpSn, List<AtenInsu> atenInsus, List<FichaPaciente> fichaPacientes, List<PersoAten> persoAtens) {
        this.fechaIngreso = fechaIngreso;
        this.fechaTermino = fechaTermino;
        this.idAtencion = idAtencion;
        this.situacion = situacion;
        this.boxIdBox = boxIdBox;
        this.tipoSesionIdTpSn = tipoSesionIdTpSn;
        this.atenInsus = atenInsus;
        this.fichaPacientes = fichaPacientes;
        this.persoAtens = persoAtens;
    }

    public Atencion() {
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Integer getIdAtencion() {
        return idAtencion;
    }

    public void setIdAtencion(Integer idAtencion) {
        this.idAtencion = idAtencion;
    }

    public Integer getSituacion() {
        return situacion;
    }

    public void setSituacion(Integer situacion) {
        this.situacion = situacion;
    }

    public Box getBoxIdBox() {
        return boxIdBox;
    }

    public void setBoxIdBox(Box boxIdBox) {
        this.boxIdBox = boxIdBox;
    }

    public TipoSesion getTipoSesionIdTpSn() {
        return tipoSesionIdTpSn;
    }

    public void setTipoSesionIdTpSn(TipoSesion tipoSesionIdTpSn) {
        this.tipoSesionIdTpSn = tipoSesionIdTpSn;
    }

    public List<AtenInsu> getAtenInsus() {
        return atenInsus;
    }

    public void setAtenInsus(List<AtenInsu> atenInsus) {
        this.atenInsus = atenInsus;
    }

    public List<FichaPaciente> getFichaPacientes() {
        return fichaPacientes;
    }

    public void setFichaPacientes(List<FichaPaciente> fichaPacientes) {
        this.fichaPacientes = fichaPacientes;
    }

    public List<PersoAten> getPersoAtens() {
        return persoAtens;
    }

    public void setPersoAtens(List<PersoAten> persoAtens) {
        this.persoAtens = persoAtens;
    }
    
    
}
