package Model;

/**
 *
 * @author Diego
 */
public class PersoAten {
    private Integer idPersoAten;
    private Atencion atencionIdAtencion;
    private Personal personalIdPersonal;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   idPersoAten=").append(idPersoAten);
        sb.append("   atencionIdAtencion=").append(atencionIdAtencion);
        sb.append("   personalIdPersonal=").append(personalIdPersonal);
        sb.append('}');
        return sb.toString();
    }

    public PersoAten() {
    }

    public PersoAten(Integer idPersoAten, Atencion atencionIdAtencion, Personal personalIdPersonal) {
        this.idPersoAten = idPersoAten;
        this.atencionIdAtencion = atencionIdAtencion;
        this.personalIdPersonal = personalIdPersonal;
    }

    public Personal getPersonalIdPersonal() {
        return personalIdPersonal;
    }

    public void setPersonalIdPersonal(Personal personalIdPersonal) {
        this.personalIdPersonal = personalIdPersonal;
    }

    public Integer getIdPersoAten() {
        return idPersoAten;
    }

    public void setIdPersoAten(Integer idPersoAten) {
        this.idPersoAten = idPersoAten;
    }

    public Atencion getAtencionIdAtencion() {
        return atencionIdAtencion;
    }

    public void setAtencionIdAtencion(Atencion atencionIdAtencion) {
        this.atencionIdAtencion = atencionIdAtencion;
    }
    
}
