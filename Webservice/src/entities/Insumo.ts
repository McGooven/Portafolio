import { Column, Entity, Index, ManyToMany } from "typeorm";
import { Atencion } from "./Atencion";

@Index("INSUMO_PK", ["idInsumo"], { unique: true })
@Entity("INSUMO")
export class Insumo {
  @Column("number", { name: "STOCK", precision: 3, scale: 0 })
  stock: number;

  @Column("number", {
    primary: true,
    name: "ID_INSUMO",
    precision: 6,
    scale: 0,
  })
  idInsumo: number;

  @Column("varchar2", { name: "NOMBRE", length: 40 })
  nombre: string;

  @ManyToMany(() => Atencion, (atencion) => atencion.insumos)
  atencions: Atencion[];
}
