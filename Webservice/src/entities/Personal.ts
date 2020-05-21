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
import { EspInter } from "./EspInter";
import { PersoAten } from "./PersoAten";
import { Centro } from "./Centro";
import { Direccion } from "./Direccion";
import { Profesion } from "./Profesion";
import { Usuario } from "./Usuario";

@Index("PERSONAL_PK", ["idPersonal"], { unique: true })
@Index("P_RUT_UNIQUE", ["rutPersonal"], { unique: true })
@Entity("PERSONAL")
export class Personal {
  @Column("varchar2", { name: "RUT_PERSONAL", unique: true, length: 15 })
  rutPersonal: string;

  @Column("varchar2", { name: "PAPELLIDO", length: 50 })
  papellido: string;

  @Column("blob", { name: "IMAGE_PERSONAL", nullable: true })
  imagePersonal: Buffer | null;

  @PrimaryGeneratedColumn({ name: "ID_PERSONAL" })
  idPersonal: number;

  @Column("varchar2", { name: "SAPELLIDO", nullable: true, length: 50 })
  sapellido: string | null;

  @Column("date", { name: "NAC_PERSONAL", nullable: true })
  nacPersonal: Date | null;

  @Column("varchar2", { name: "SNOMBRE", nullable: true, length: 50 })
  snombre: string | null;

  @Column("char", { name: "HABILITADO", length: 2 })
  habilitado: string;

  @Column("varchar2", { name: "PNOMBRE", length: 50 })
  pnombre: string;

  @Column("date", { name: "ANIO_INGRESO" })
  anioIngreso: Date;

  @ManyToMany(() => EspInter, (espInter) => espInter.personals)
  espInters: EspInter[];

  @OneToMany(() => PersoAten, (persoAten) => persoAten.personalIdPersonal)
  persoAtens: PersoAten[];

  @ManyToOne(() => Centro, (centro) => centro.personals)
  @JoinColumn([{ name: "CENTRO_ID_CENTRO", referencedColumnName: "idCentro" }])
  centroIdCentro: Centro;

  @ManyToOne(() => Direccion, (direccion) => direccion.personals)
  @JoinColumn([
    { name: "DIRECCION_ID_DIRECCION", referencedColumnName: "idDireccion" },
  ])
  direccionIdDireccion: Direccion;

  @OneToMany(() => Profesion, (profesion) => profesion.personalIdPersonal)
  profesions: Profesion[];

  @OneToMany(() => Usuario, (usuario) => usuario.personalIdPersonal)
  usuarios: Usuario[];
}
