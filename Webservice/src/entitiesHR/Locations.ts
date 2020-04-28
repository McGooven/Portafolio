import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Departments } from "./Departments";
import { Countries } from "./Countries";

@Index("LOC_CITY_IX", ["city"], {})
@Index("LOC_COUNTRY_IX", ["countryId"], {})
@Index("LOC_ID_PK", ["locationId"], { unique: true })
@Index("LOC_STATE_PROVINCE_IX", ["stateProvince"], {})
@Entity("LOCATIONS")
export class Locations {
  @Column("number", {
    primary: true,
    name: "LOCATION_ID",
    precision: 4,
    scale: 0,
  })
  locationId: number;

  @Column("varchar2", { name: "STREET_ADDRESS", nullable: true, length: 40 })
  streetAddress: string | null;

  @Column("varchar2", { name: "POSTAL_CODE", nullable: true, length: 12 })
  postalCode: string | null;

  @Column("varchar2", { name: "CITY", length: 30 })
  city: string;

  @Column("varchar2", { name: "STATE_PROVINCE", nullable: true, length: 25 })
  stateProvince: string | null;

  @Column("char", { name: "COUNTRY_ID", nullable: true, length: 2 })
  countryId: string | null;

  @OneToMany(() => Departments, (departments) => departments.location)
  departments: Departments[];

  @ManyToOne(() => Countries, (countries) => countries.locations)
  @JoinColumn([{ name: "COUNTRY_ID", referencedColumnName: "countryId" }])
  country: Countries;
}
