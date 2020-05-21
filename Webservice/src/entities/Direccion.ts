import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
  PrimaryGeneratedColumn,
} from "typeorm";
import { Centro } from "./Centro";
import { Comuna } from "./Comuna";
import { FichaPaciente } from "./FichaPaciente";
import { Personal } from "./Personal";

@Index("DIRECCION_PK", ["idDireccion"], { unique: true })
@Entity("DIRECCION")
export class Direccion {
  @Column("varchar2", { name: "DIRECCION", length: 200 })
  direccion: string;

  @PrimaryGeneratedColumn({name: "ID_DIRECCION" })
  idDireccion: number;

  @OneToMany(() => Centro, (centro) => centro.direccionIdDireccion)
  centros: Centro[];

  @ManyToOne(() => Comuna, (comuna) => comuna.direccions)
  @JoinColumn([{ name: "COMUNA_COMUNA_ID", referencedColumnName: "idComuna" }])
  comunaComuna: Comuna;

  @OneToMany(
    () => FichaPaciente,
    (fichaPaciente) => fichaPaciente.direccionIdDireccion
  )
  fichaPacientes: FichaPaciente[];

  @OneToMany(() => Personal, (personal) => personal.direccionIdDireccion)
  personals: Personal[];
}
