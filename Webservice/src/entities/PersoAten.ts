import { Column, Entity, Index, JoinColumn, ManyToOne } from "typeorm";
import { Atencion } from "./Atencion";
import { Personal } from "./Personal";

@Index("PERSO_ATEN_PK", ["idPersoAten"], { unique: true })
@Entity("PERSO_ATEN")
export class PersoAten {
  @Column("number", {
    primary: true,
    name: "ID_PERSO_ATEN",
    precision: 18,
    scale: 0,
  })
  idPersoAten: number;

  @ManyToOne(() => Atencion, (atencion) => atencion.persoAtens)
  @JoinColumn([
    { name: "ATENCION_ID_ATENCION", referencedColumnName: "idAtencion" },
  ])
  atencionIdAtencion: Atencion;

  @ManyToOne(() => Personal, (personal) => personal.persoAtens)
  @JoinColumn([
    { name: "PERSONAL_ID_PERSONAL", referencedColumnName: "idPersonal" },
  ])
  personalIdPersonal: Personal;
}
