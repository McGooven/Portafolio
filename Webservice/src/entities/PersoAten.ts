import { Column, Entity, Index, JoinColumn, ManyToOne,PrimaryGeneratedColumn } from "typeorm";
import { Atencion } from "./Atencion";
import { Personal } from "./Personal";

@Index("PERSO_ATEN_PK", ["idPersoAten"], { unique: true })
@Entity("PERSO_ATEN")
export class PersoAten {
  @PrimaryGeneratedColumn({name: "ID_PERSO_ATEN" })
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
