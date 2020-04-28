import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Locations } from "./Locations";
import { Employees } from "./Employees";
import { JobHistory } from "./JobHistory";

@Index("DEPT_ID_PK", ["departmentId"], { unique: true })
@Index("DEPT_LOCATION_IX", ["locationId"], {})
@Entity("DEPARTMENTS")
export class Departments {
  @Column("number", {
    primary: true,
    name: "DEPARTMENT_ID",
    precision: 4,
    scale: 0,
  })
  departmentId: number;

  @Column("varchar2", { name: "DEPARTMENT_NAME", length: 30 })
  departmentName: string;

  @Column("number", {
    name: "LOCATION_ID",
    nullable: true,
    precision: 4,
    scale: 0,
  })
  locationId: number | null;

  @ManyToOne(() => Locations, (locations) => locations.departments)
  @JoinColumn([{ name: "LOCATION_ID", referencedColumnName: "locationId" }])
  location: Locations;

  @ManyToOne(() => Employees, (employees) => employees.departments)
  @JoinColumn([{ name: "MANAGER_ID", referencedColumnName: "employeeId" }])
  manager: Employees;

  @OneToMany(() => Employees, (employees) => employees.department)
  employees: Employees[];

  @OneToMany(() => JobHistory, (jobHistory) => jobHistory.department)
  jobHistories: JobHistory[];
}
