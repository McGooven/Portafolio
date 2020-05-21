package Model;

/**
 *
 * @author Diego
 */
public class Usuario {
    private Integer idUsuario;
    private String habilitado;
    private Integer permisos;
    private String password;
    private String correo;
    private FichaPaciente fichaPacienteIdFicha;
    private Personal personalIdPersonal;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("   idUsuario=").append(idUsuario);
        sb.append("   habilitado=").append(habilitado);
        sb.append("   permisos=").append(permisos);
        sb.append("   password=").append(password);
        sb.append("   correo=").append(correo);
        sb.append("   fichaPacienteIdFicha=").append(fichaPacienteIdFicha);
        sb.append("   personalIdPersonal=").append(personalIdPersonal);
        sb.append('}');
        return sb.toString();
    }

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String habilitado, EspInter permisos, String password, String correo, FichaPaciente fichaPacienteIdFicha, Personal personalIdPersonal) {
        this.idUsuario = idUsuario;
        this.habilitado = habilitado;
        this.permisos = permisos.getIdEspecialidad();
        this.password = password;
        this.correo = correo;
        this.fichaPacienteIdFicha = fichaPacienteIdFicha;
        this.personalIdPersonal = personalIdPersonal;
    }

    public Personal getPersonalIdPersonal() {
        return personalIdPersonal;
    }

    public void setPersonalIdPersonal(Personal personalIdPersonal) {
        this.personalIdPersonal = personalIdPersonal;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public Integer getPermisos() {
        return permisos;
    }

    public void setPermisos(EspInter permisos) {
        if(permisos.getIdEspecialidad() == 1 || permisos.getIdEspecialidad() == 2){
            this.permisos = 4;
        }
        else if(permisos.getIdEspecialidad() == 3){
            this.permisos = 3;
        }
        else if(permisos.getIdEspecialidad() == 5){
            this.permisos = 1;
        }
        else if(permisos.getIdEspecialidad() == 6){
            this.permisos = 2;
        }
        else{
            this.permisos = 5;
        }
        
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public FichaPaciente getFichaPacienteIdFicha() {
        return fichaPacienteIdFicha;
    }

    public void setFichaPacienteIdFicha(FichaPaciente fichaPacienteIdFicha) {
        this.fichaPacienteIdFicha = fichaPacienteIdFicha;
    }
    
}
