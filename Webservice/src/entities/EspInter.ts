import { Column, Entity, Index, JoinTable, ManyToMany } from "typeorm";
import { Personal } from "./Personal";

@Index("ESPECIALIDAD_INTERNA_PK", ["idEspecialidad"], { unique: true })
@Entity("ESP_INTER")
export class EspInter {
  @Column("varchar2", { name: "NOMBRE", length: 30 })
  nombre: string;

  @Column("number", { primary: true, name: "ID_ESPECIALIDAD" })
  idEspecialidad: number;

  @ManyToMany(() => Personal, (personal) => personal.espInters)
  @JoinTable({
    name: "ESP_PER",
    joinColumns: [
      {
        name: "ESP_INTER_ID_ESPECIALIDAD",
        referencedColumnName: "idEspecialidad",
      },
    ],
    inverseJoinColumns: [
      { name: "PERSONAL_ID_PERSONAL", referencedColumnName: "idPersonal" },
    ],
  })
  personals: Personal[];
}
