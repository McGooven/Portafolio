import { Column, Entity, Index, JoinColumn, ManyToOne } from "typeorm";
import { Atencion } from "./Atencion";
import { Insumo } from "./Insumo";

@Index("RELATION_1_PK", ["atencionIdAtencion", "insumoIdInsumo"], {
  unique: true,
})
@Entity("ATEN_INSU")
export class AtenInsu {
  @Column("number", { primary: true, name: "ATENCION_ID_ATENCION" })
  atencionIdAtencion: number;

  @Column("number", { name: "CANTIDAD" })
  cantidad: number;

  @Column("number", { primary: true, name: "INSUMO_ID_INSUMO" })
  insumoIdInsumo: number;

  @ManyToOne(() => Atencion, (atencion) => atencion.atenInsus)
  @JoinColumn([
    { name: "ATENCION_ID_ATENCION", referencedColumnName: "idAtencion" },
  ])
  atencionIdAtencion2: Atencion;

  @ManyToOne(() => Insumo, (insumo) => insumo.atenInsus)
  @JoinColumn([{ name: "INSUMO_ID_INSUMO", referencedColumnName: "idInsumo" }])
  insumoIdInsumo2: Insumo;
}
