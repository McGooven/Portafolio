import { Column, Entity, Index, JoinColumn, ManyToOne,PrimaryGeneratedColumn } from "typeorm";
import { FichaPaciente } from "./FichaPaciente";
import { Personal } from "./Personal";

@Index("USUARIO_PK", ["idUsuario"], { unique: true })
@Entity("USUARIO")
export class Usuario {
  @Column("char", { name: "HABILITADO", length: 1 })
  habilitado: string;

  @PrimaryGeneratedColumn({name: "ID_USUARIO" })
  idUsuario: number;

  @Column("number", { name: "PERMISOS" })
  permisos: number;

  @Column("varchar2", { name: "PASSWORD", length: 12 })
  password: string;

  @Column("varchar2", { name: "CORREO", length: 50 })
  correo: string;

  @ManyToOne(() => FichaPaciente, (fichaPaciente) => fichaPaciente.usuarios,{nullable:true})
  @JoinColumn([
    { name: "FICHA_PACIENTE_ID_FICHA", referencedColumnName: "idFicha" },
  ])
  fichaPacienteIdFicha: FichaPaciente;

  @ManyToOne(() => Personal, (personal) => personal.usuarios,{nullable:true})
  @JoinColumn([
    { name: "PERSONAL_ID_PERSONAL", referencedColumnName: "idPersonal" },
  ])
  personalIdPersonal: Personal;
}
