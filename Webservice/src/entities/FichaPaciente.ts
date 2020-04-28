import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
  OneToOne,
} from "typeorm";
import { Atencion } from "./Atencion";
import { Centro } from "./Centro";
import { Usuario } from "./Usuario";

@Index("FICHA_PACIENTE__IDX", ["usuarioIdUsuario"], { unique: true })
@Index("FICHA_PACIENTE_PK", ["idFicha"], { unique: true })
@Entity("FICHA_PACIENTE")
export class FichaPaciente {
  @Column("number", { name: "USUARIO_ID_USUARIO", precision: 6, scale: 0 })
  usuarioIdUsuario: number;

  @Column("number", { name: "ETAPA", precision: 1, scale: 0 })
  etapa: number;

  @Column("varchar2", { name: "RUT", length: 10 })
  rut: string;

  @Column("number", { primary: true, name: "ID_FICHA", precision: 5, scale: 0 })
  idFicha: number;

  @Column("varchar2", { name: "PAPELLIDO", length: 20 })
  papellido: string;

  @Column("varchar2", { name: "PNOMBRE", length: 20 })
  pnombre: string;

  @OneToMany(() => Atencion, (atencion) => atencion.fichaPacienteIdFicha)
  atencions: Atencion[];

  @ManyToOne(() => Centro, (centro) => centro.fichaPacientes)
  @JoinColumn([{ name: "CENTRO_ID_CENTRO", referencedColumnName: "idCentro" }])
  centroIdCentro: Centro;

  @OneToOne(() => Usuario, (usuario) => usuario.fichaPaciente)
  @JoinColumn([
    { name: "USUARIO_ID_USUARIO", referencedColumnName: "idUsuario" },
  ])
  usuarioIdUsuario2: Usuario;
}
