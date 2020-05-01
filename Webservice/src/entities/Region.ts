import { Column, Entity, Index, OneToMany } from "typeorm";
import { Comuna } from "./Comuna";

@Index("REGION_PK", ["idRegion"], { unique: true })
@Entity("REGION")
export class Region {
  @Column("varchar2", { name: "NOMBRE", length: 60 })
  nombre: string;

  @Column("number", { primary: true, name: "ID_REGION" })
  idRegion: number;

  @OneToMany(() => Comuna, (comuna) => comuna.regionIdRegion)
  comunas: Comuna[];
}
