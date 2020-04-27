import { Column, Entity, Index, JoinColumn, ManyToOne } from "typeorm";
import { Personal } from "./Personal";

@Index("ESP_INTERNA_PK", ["idEspecialidad"], { unique: true })
@Entity("ESP_INTERNA")
export class EspInterna {
  @Column("number", {
    primary: true,
    name: "ID_ESPECIALIDAD",
    precision: 6,
    scale: 0,
  })
  idEspecialidad: number;

  @Column("varchar2", { name: "NOMBRE", length: 30 })
  nombre: string;

  @ManyToOne(() => Personal, (personal) => personal.espInternas)
  @JoinColumn([
    { name: "PERSONAL_ID_PERSONAL", referencedColumnName: "idPersonal" },
  ])
  personalIdPersonal: Personal;
}
