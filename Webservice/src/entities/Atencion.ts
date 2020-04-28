import {
  Column,
  Entity,
  Index,
  JoinColumn,
  JoinTable,
  ManyToMany,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Box } from "./Box";
import { FichaPaciente } from "./FichaPaciente";
import { PersoAten } from "./PersoAten";
import { Insumo } from "./Insumo";

@Index("ATENCION_PK", ["idAtencion"], { unique: true })
@Entity("ATENCION")
export class Atencion {
  @Column("number", {
    primary: true,
    name: "ID_ATENCION",
    precision: 10,
    scale: 0,
  })
  idAtencion: number;

  @Column("char", { name: "VALIDA", length: 1 })
  valida: string;

  @ManyToOne(() => Box, (box) => box.atencions)
  @JoinColumn([{ name: "BOX_ID_BOX", referencedColumnName: "idBox" }])
  boxIdBox: Box;

  @ManyToOne(() => FichaPaciente, (fichaPaciente) => fichaPaciente.atencions)
  @JoinColumn([
    { name: "FICHA_PACIENTE_ID_FICHA", referencedColumnName: "idFicha" },
  ])
  fichaPacienteIdFicha: FichaPaciente;

  @OneToMany(() => PersoAten, (persoAten) => persoAten.atencionIdAtencion)
  persoAtens: PersoAten[];

  @ManyToMany(() => Insumo, (insumo) => insumo.atencions)
  @JoinTable({
    name: "RELATION_1",
    joinColumns: [
      { name: "ATENCION_ID_ATENCION", referencedColumnName: "idAtencion" },
    ],
    inverseJoinColumns: [
      { name: "INSUMO_ID_INSUMO", referencedColumnName: "idInsumo" },
    ],
  })
  insumos: Insumo[];
}
