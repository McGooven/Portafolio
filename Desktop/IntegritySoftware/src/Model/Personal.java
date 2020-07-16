package Model;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Diego
 */
public class Personal {
    private Integer idPersonal;
    private String rutPersonal;
    private String papellido;
    private String pnombre;
    private String snombre;
    private String sapellido;
    private File imagePersonal;
    private String nacPersonal;
    private String habilitado;
    private String anioIngreso;
    private Centro centroIdCentro;
    private Direccion direccionIdDireccion;
    private List<EspInter> espInters;
    private List<PersoAten> persoAtens;
    private List<Profesion> profesions;
    private List<Usuario> usuarios;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   idPersonal=").append(idPersonal);
        sb.append("   rutPersonal=").append(rutPersonal);
        sb.append("   pnombre=").append(pnombre);
        sb.append("   snombre=").append(snombre);
        sb.append("   papellido=").append(papellido);
        sb.append("   sapellido=").append(sapellido);
        sb.append("   imagePersonal=").append(imagePersonal);
        sb.append("   nacPersonal=").append(nacPersonal);
        sb.append("   habilitado=").append(habilitado);
        sb.append("   anioIngreso=").append(anioIngreso);
        sb.append("   centroIdCentro=").append(centroIdCentro);
        sb.append("   direccionIdDireccion=").append(direccionIdDireccion);
        sb.append("   espInters=").append(espInters);
        sb.append("   persoAtens=").append(persoAtens);
        sb.append("   profesions=").append(profesions);
        sb.append("   usuarios=").append(usuarios);
        sb.append('}');
        return sb.toString();
    }

    public Personal() {
    }

    public Personal(Integer idPersonal, String rutPersonal, String pnombre, String snombre, String papellido, String sapellido, File imagePersonal, String nacPersonal, String habilitado, String anioIngreso, Centro centroIdCentro, Direccion direccionIdDireccion, List<EspInter> espInters, List<PersoAten> persoAtens, List<Profesion> profesions, List<Usuario> usuarios) {
        this.idPersonal = idPersonal;
        this.rutPersonal = rutPersonal;
        this.pnombre = pnombre;
        this.snombre = snombre;
        this.papellido = papellido;
        this.sapellido = sapellido;
        this.imagePersonal = imagePersonal;
        this.nacPersonal = nacPersonal;
        this.habilitado = habilitado;
        this.anioIngreso = anioIngreso;
        this.centroIdCentro = centroIdCentro;
        this.direccionIdDireccion = direccionIdDireccion;
        this.espInters = espInters;
        this.persoAtens = persoAtens;
        this.profesions = profesions;
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Integer getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(Integer idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getRutPersonal() {
        return rutPersonal;
    }

    public void setRutPersonal(String rutPersonal) {
        this.rutPersonal = rutPersonal;
    }

    public String getPnombre() {
        return pnombre;
    }

    public void setPnombre(String pnombre) {
        this.pnombre = pnombre;
    }

    public String getSnombre() {
        return snombre;
    }

    public void setSnombre(String snombre) {
        this.snombre = snombre;
    }

    public String getPapellido() {
        return papellido;
    }

    public void setPapellido(String papellido) {
        this.papellido = papellido;
    }

    public String getSapellido() {
        return sapellido;
    }

    public void setSapellido(String sapellido) {
        this.sapellido = sapellido;
    }

    public File getImagePersonal() {
        return imagePersonal;
    }

    public void setImagePersonal(File imagePersonal) {
        this.imagePersonal = imagePersonal;
    }

    public String getNacPersonal() {
        return nacPersonal;
    }

    public void setNacPersonal(String nacPersonal) {
        this.nacPersonal = nacPersonal;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public String getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(String anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public Centro getCentroIdCentro() {
        return centroIdCentro;
    }

    public void setCentroIdCentro(Centro centroIdCentro) {
        this.centroIdCentro = centroIdCentro;
    }

    public Direccion getDireccionIdDireccion() {
        return direccionIdDireccion;
    }

    public void setDireccionIdDireccion(Direccion direccionIdDireccion) {
        this.direccionIdDireccion = direccionIdDireccion;
    }

    public List<EspInter> getEspInters() {
        return espInters;
    }

    public void setEspInters(List<EspInter> espInters) {
        this.espInters = espInters;
    }

    public List<PersoAten> getPersoAtens() {
        return persoAtens;
    }

    public void setPersoAtens(List<PersoAten> persoAtens) {
        this.persoAtens = persoAtens;
    }

    public List<Profesion> getProfesions() {
        return profesions;
    }

    public void setProfesions(List<Profesion> profesions) {
        this.profesions = profesions;
    }
    
}
