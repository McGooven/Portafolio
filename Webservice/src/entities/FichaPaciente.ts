import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToMany,
  ManyToOne,
  OneToMany,
  PrimaryGeneratedColumn
} from "typeorm";
import { Atencion } from "./Atencion";
import { Centro } from "./Centro";
import { Direccion } from "./Direccion";
import { Usuario } from "./Usuario";

@Index("FICHA_PACIENTE_PK", ["idFicha"], { unique: true })
@Index("FP_RUT_UNIQUE", ["rutPaciente"], { unique: true })
@Entity("FICHA_PACIENTE")
export class FichaPaciente {
  @Column("varchar2", { name: "SAPELLIDO", nullable: true, length: 50 })
  sapellido: string | null;

  @Column("blob", { name: "IMAGE_PACIENTE", nullable: true })
  imagePaciente: Buffer | null;

  @Column("varchar2", { name: "SNOMBRE", nullable: true, length: 50 })
  snombre: string | null;

  @Column("varchar2", { name: "RUT_PACIENTE", unique: true, length: 15 })
  rutPaciente: string;

  @Column("number", { name: "ETAPA" })
  etapa: number;

  @PrimaryGeneratedColumn({name: "ID_FICHA" })
  idFicha: number;

  @Column("char", { name: "HABILITADO", length: 1 })
  habilitado: string;

  @Column("date", { name: "NAC_PACIENTE", nullable: true })
  nacPaciente: Date | null;

  @Column("varchar2", { name: "PNOMBRE", length: 50 })
  pnombre: string;

  @Column("varchar2", { name: "PAPELLIDO", length: 50 })
  papellido: string;

  @ManyToMany(() => Atencion, (atencion) => atencion.fichaPacientes)
  atencions: Atencion[];

  @ManyToOne(() => Centro, (centro) => centro.fichaPacientes)
  @JoinColumn([{ name: "CENTRO_ID_CENTRO", referencedColumnName: "idCentro" }])
  centroIdCentro: Centro;

  @ManyToOne(() => Direccion, (direccion) => direccion.fichaPacientes)
  @JoinColumn([
    { name: "DIRECCION_ID_DIRECCION", referencedColumnName: "idDireccion" },
  ])
  direccionIdDireccion: Direccion;

  @OneToMany(() => Usuario, (usuario) => usuario.fichaPacienteIdFicha)
  usuarios: Usuario[];
}
