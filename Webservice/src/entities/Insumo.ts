import { Column, Entity, Index, OneToMany,PrimaryGeneratedColumn } from "typeorm";
import { AtenInsu } from "./AtenInsu";

@Index("INSUMO_PK", ["idInsumo"], { unique: true })
@Entity("INSUMO")
export class Insumo {
  @Column("number", { name: "STOCK" })
  stock: number;

  @Column("varchar2", { name: "NOMBRE", length: 50 })
  nombre: string;

  @PrimaryGeneratedColumn({ name: "ID_INSUMO" })
  idInsumo: number;

  @Column("varchar2", { name: "UNIDAD_MEDIDA", length: 20 })
  unidadMedida: string;

  @Column("char", { name: "HABILITADO", length: 1 })
  habilitado: string;

  @OneToMany(() => AtenInsu, (atenInsu) => atenInsu.insumoIdInsumo2)
  atenInsus: AtenInsu[];
}
