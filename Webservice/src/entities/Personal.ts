import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
  OneToOne,
} from "typeorm";
import { EspInterna } from "./EspInterna";
import { PersoAten } from "./PersoAten";
import { Centro } from "./Centro";
import { Usuario } from "./Usuario";
import { Profesion } from "./Profesion";

@Index("PERSONAL__IDX", ["usuarioIdUsuario"], { unique: true })
@Index("PERSONAL_PK", ["idPersonal"], { unique: true })
@Entity("PERSONAL")
export class Personal {
  @Column("varchar2", { name: "SNOMBRE", length: 50 })
  snombre: string;

  @Column("number", { name: "USUARIO_ID_USUARIO", precision: 6, scale: 0 })
  usuarioIdUsuario: number;

  @Column("number", {
    primary: true,
    name: "ID_PERSONAL",
    precision: 12,
    scale: 3,
  })
  idPersonal: number;

  @Column("varchar2", { name: "PAPELLIDO", length: 50 })
  papellido: string;

  @Column("varchar2", { name: "DIRECCION", length: 50 })
  direccion: string;

  @Column("varchar2", { name: "PNOMBRE", length: 50 })
  pnombre: string;

  @Column("varchar2", { name: "SAPELLIDO", length: 50 })
  sapellido: string;

  @OneToMany(() => EspInterna, (espInterna) => espInterna.personalIdPersonal)
  espInternas: EspInterna[];

  @OneToMany(() => PersoAten, (persoAten) => persoAten.personalIdPersonal)
  persoAtens: PersoAten[];

  @ManyToOne(() => Centro, (centro) => centro.personals)
  @JoinColumn([{ name: "CENTRO_ID_CENTRO", referencedColumnName: "idCentro" }])
  centroIdCentro: Centro;

  @OneToOne(() => Usuario, (usuario) => usuario.personal)
  @JoinColumn([
    { name: "USUARIO_ID_USUARIO", referencedColumnName: "idUsuario" },
  ])
  usuarioIdUsuario2: Usuario;

  @OneToMany(() => Profesion, (profesion) => profesion.personalIdPersonal)
  profesions: Profesion[];
}
