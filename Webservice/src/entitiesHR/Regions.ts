import { Column, Entity, Index, OneToMany } from "typeorm";
import { Countries } from "./Countries";

@Index("REG_ID_PK", ["regionId"], { unique: true })
@Entity("REGIONS")
export class Regions {
  @Column("number", { primary: true, name: "REGION_ID" })
  regionId: number;

  @Column("varchar2", { name: "REGION_NAME", nullable: true, length: 25 })
  regionName: string | null;

  @OneToMany(() => Countries, (countries) => countries.region)
  countries: Countries[];
}
