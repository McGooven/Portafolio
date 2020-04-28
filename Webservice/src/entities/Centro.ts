import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Box } from "./Box";
import { Direccion } from "./Direccion";
import { FichaPaciente } from "./FichaPaciente";
import { Personal } from "./Personal";

@Index("CENTRO_PK", ["idCentro"], { unique: true })
@Entity("CENTRO")
export class Centro {
  @Column("number", { primary: true, name: "ID_CENTRO" })
  idCentro: number;

  @Column("varchar2", { name: "NOMBRE_SEDE", length: 100 })
  nombreSede: string;

  @OneToMany(() => Box, (box) => box.centroIdCentro)
  boxes: Box[];

  @ManyToOne(() => Direccion, (direccion) => direccion.centros)
  @JoinColumn([
    { name: "DIRECCION_ID_DIRECCION", referencedColumnName: "idDireccion" },
  ])
  direccionIdDireccion: Direccion;

  @OneToMany(
    () => FichaPaciente,
    (fichaPaciente) => fichaPaciente.centroIdCentro
  )
  fichaPacientes: FichaPaciente[];

  @OneToMany(() => Personal, (personal) => personal.centroIdCentro)
  personals: Personal[];
}
