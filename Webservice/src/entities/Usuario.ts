import { Column, Entity, Index, OneToOne } from "typeorm";
import { FichaPaciente } from "./FichaPaciente";
import { Personal } from "./Personal";

@Index("USUARIO_PK", ["idUsuario"], { unique: true })
@Entity("USUARIO")
export class Usuario {
  @Column("number", { name: "TIPO_USUARIO", precision: 1, scale: 0 })
  tipoUsuario: number;

  @Column("varchar2", { name: "CORREO", length: 20 })
  correo: string;

  @Column("varchar2", { name: "PASSWORD", length: 12 })
  password: string;

  @Column("number", {
    primary: true,
    name: "ID_USUARIO",
    precision: 6,
    scale: 0,
  })
  idUsuario: number;

  @OneToOne(
    () => FichaPaciente,
    (fichaPaciente) => fichaPaciente.usuarioIdUsuario2
  )
  fichaPaciente: FichaPaciente;

  @OneToOne(() => Personal, (personal) => personal.usuarioIdUsuario2)
  personal: Personal;
}
