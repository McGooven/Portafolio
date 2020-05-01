import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Region } from "./Region";
import { Direccion } from "./Direccion";

@Index("COMUNA_PK", ["idComuna"], { unique: true })
@Entity("COMUNA")
export class Comuna {
  @Column("number", { primary: true, name: "ID_COMUNA" })
  idComuna: number;

  @Column("varchar2", { name: "NOMBRE_COMUNA", length: 50 })
  nombreComuna: string;

  @ManyToOne(() => Region, (region) => region.comunas)
  @JoinColumn([{ name: "REGION_ID_REGION", referencedColumnName: "idRegion" }])
  regionIdRegion: Region;

  @OneToMany(() => Direccion, (direccion) => direccion.comunaComuna)
  direccions: Direccion[];
}
