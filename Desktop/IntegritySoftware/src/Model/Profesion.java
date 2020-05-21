package Model;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Diego
 */
public class Profesion {
    private Integer idProfesion;
    private File certificado;
    private String titulo;
    private String fechaEgreso;
    private String casaEstudio;
    private Personal personalIdPersonal;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   idProfesion=").append(idProfesion);
        sb.append("   certificado=").append(certificado);
        sb.append("   titulo=").append(titulo);
        sb.append("   fechaEgreso=").append(fechaEgreso);
        sb.append("   casaEstudio=").append(casaEstudio);
        sb.append("   personalIdPersonal=").append(personalIdPersonal);
        sb.append('}');
        return sb.toString();
    }

    public Profesion() {
    }

    public Profesion(Integer idProfesion, File certificado, String titulo, String fechaEgreso, String casaEstudio, Personal personalIdPersonal) {
        this.idProfesion = idProfesion;
        this.certificado = certificado;
        this.titulo = titulo;
        this.fechaEgreso = fechaEgreso;
        this.casaEstudio = casaEstudio;
        this.personalIdPersonal = personalIdPersonal;
    }

    public Personal getPersonalIdPersonal() {
        return personalIdPersonal;
    }

    public void setPersonalIdPersonal(Personal personalIdPersonal) {
        this.personalIdPersonal = personalIdPersonal;
    }

    public Integer getIdProfesion() {
        return idProfesion;
    }

    public void setIdProfesion(Integer idProfesion) {
        this.idProfesion = idProfesion;
    }

    public File getCertificado() {
        return certificado;
    }

    public void setCertificado(File certificado) {
        this.certificado = certificado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(String fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public String getCasaEstudio() {
        return casaEstudio;
    }

    public void setCasaEstudio(String casaEstudio) {
        this.casaEstudio = casaEstudio;
    }
    
}
