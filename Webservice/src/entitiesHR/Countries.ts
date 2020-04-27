import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Regions } from "./Regions";
import { Locations } from "./Locations";

@Index("COUNTRY_C_ID_PK", ["countryId"], { unique: true })
@Entity("COUNTRIES")
export class Countries {
  @Column("char", { primary: true, name: "COUNTRY_ID", length: 2 })
  countryId: string;

  @Column("varchar2", { name: "COUNTRY_NAME", nullable: true, length: 40 })
  countryName: string | null;

  @ManyToOne(() => Regions, (regions) => regions.countries)
  @JoinColumn([{ name: "REGION_ID", referencedColumnName: "regionId" }])
  region: Regions;

  @OneToMany(() => Locations, (locations) => locations.country)
  locations: Locations[];
}
