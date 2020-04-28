import { Column, Entity, Index, OneToMany } from "typeorm";
import { Box } from "./Box";
import { FichaPaciente } from "./FichaPaciente";
import { Personal } from "./Personal";

@Index("CENTRO_PK", ["idCentro"], { unique: true })
@Entity("CENTRO")
export class Centro {
  @Column("number", { name: "CANTIDAD_BOX", precision: 2, scale: 0 })
  cantidadBox: number;

  @Column("varchar2", { name: "DIRECCION", length: 50 })
  direccion: string;

  @Column("number", {
    primary: true,
    name: "ID_CENTRO",
    precision: 6,
    scale: 0,
  })
  idCentro: number;

  @OneToMany(() => Box, (box) => box.centroIdCentro)
  boxes: Box[];

  @OneToMany(
    () => FichaPaciente,
    (fichaPaciente) => fichaPaciente.centroIdCentro
  )
  fichaPacientes: FichaPaciente[];

  @OneToMany(() => Personal, (personal) => personal.centroIdCentro)
  personals: Personal[];
}
