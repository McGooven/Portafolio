import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
  PrimaryGeneratedColumn
} from "typeorm";
import { Atencion } from "./Atencion";
import { Centro } from "./Centro";

@Index("BOX_PK", ["idBox"], { unique: true })
@Entity("BOX")
export class Box {
  @PrimaryGeneratedColumn({name: "ID_BOX" })
  idBox: number;

  @Column("char", { name: "HABILITADA", length: 1 })
  habilitada: string;

  @Column("varchar2", { name: "ESTADO", length: 20 })
  estado: string;

  @OneToMany(() => Atencion, (atencion) => atencion.boxIdBox)
  atencions: Atencion[];

  @ManyToOne(() => Centro, (centro) => centro.boxes)
  @JoinColumn([{ name: "CENTRO_ID_CENTRO", referencedColumnName: "idCentro" }])
  centroIdCentro: Centro;
}
