import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Atencion } from "./Atencion";
import { Centro } from "./Centro";

@Index("BOX_PK", ["idBox"], { unique: true })
@Entity("BOX")
export class Box {
  @Column("number", { primary: true, name: "ID_BOX", precision: 3, scale: 0 })
  idBox: number;

  @Column("varchar2", { name: "ESTADO", length: 20 })
  estado: string;

  @OneToMany(() => Atencion, (atencion) => atencion.boxIdBox)
  atencions: Atencion[];

  @ManyToOne(() => Centro, (centro) => centro.boxes)
  @JoinColumn([{ name: "CENTRO_ID_CENTRO", referencedColumnName: "idCentro" }])
  centroIdCentro: Centro;
}
