package Model;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Diego
 */
public class FichaPaciente {
    private Integer idFicha;
    private Integer etapa;
    private String rutPaciente;
    private String papellido;
    private String pnombre;
    private String snombre;
    private String sapellido;
    private File imagePaciente;
    private String nacPaciente;
    private String habilitado;
    private Centro centroIdCentro;
    private Direccion direccionIdDireccion;
    private List<Usuario> usuarios;
    private List<Atencion> atencions;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   idFicha=").append(idFicha);
        sb.append("   etapa=").append(etapa);
        sb.append("   rutPaciente=").append(rutPaciente);
        sb.append("   papellido=").append(papellido);
        sb.append("   pnombre=").append(pnombre);
        sb.append("   snombre=").append(snombre);
        sb.append("   sapellido=").append(sapellido);
        sb.append("   imagePaciente=").append(imagePaciente);
        sb.append("   nacPaciente=").append(nacPaciente);
        sb.append("   habilitado=").append(habilitado);
        sb.append("   centroIdCentro=").append(centroIdCentro);
        sb.append("   direccionIdDireccion=").append(direccionIdDireccion);
        sb.append("   usuarios=").append(usuarios);
        sb.append("   atencions=").append(atencions);
        sb.append('}');
        return sb.toString();
    }

    public FichaPaciente() {
    }

    public FichaPaciente(Integer idFicha, Integer etapa, String rutPaciente, String papellido, String pnombre, String snombre, String sapellido, File imagePaciente, String nacPaciente, String habilitado, Centro centroIdCentro, Direccion direccionIdDireccion, List<Usuario> usuarios, List<Atencion> atencions) {
        this.idFicha = idFicha;
        this.etapa = etapa;
        this.rutPaciente = rutPaciente;
        this.papellido = papellido;
        this.pnombre = pnombre;
        this.snombre = snombre;
        this.sapellido = sapellido;
        this.imagePaciente = imagePaciente;
        this.nacPaciente = nacPaciente;
        this.habilitado = habilitado;
        this.centroIdCentro = centroIdCentro;
        this.direccionIdDireccion = direccionIdDireccion;
        this.usuarios = usuarios;
        this.atencions = atencions;
    }

    public Integer getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(Integer idFicha) {
        this.idFicha = idFicha;
    }

    public Integer getEtapa() {
        return etapa;
    }

    public void setEtapa(Integer etapa) {
        this.etapa = etapa;
    }

    public String getRutPaciente() {
        return rutPaciente;
    }

    public void setRutPaciente(String rutPaciente) {
        this.rutPaciente = rutPaciente;
    }

    public String getPapellido() {
        return papellido;
    }

    public void setPapellido(String papellido) {
        this.papellido = papellido;
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

    public String getSapellido() {
        return sapellido;
    }

    public void setSapellido(String sapellido) {
        this.sapellido = sapellido;
    }

    public File getImagePaciente() {
        return imagePaciente;
    }

    public void setImagePaciente(File imagePaciente) {
        this.imagePaciente = imagePaciente;
    }

    public String getNacPaciente() {
        return nacPaciente;
    }

    public void setNacPaciente(String nacPaciente) {
        this.nacPaciente = nacPaciente;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Atencion> getAtencions() {
        return atencions;
    }

    public void setAtencions(List<Atencion> atencions) {
        this.atencions = atencions;
    }
    
}
