import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Departments } from "./Departments";
import { Jobs } from "./Jobs";
import { JobHistory } from "./JobHistory";

@Index("EMP_DEPARTMENT_IX", ["departmentId"], {})
@Index("EMP_EMAIL_UK", ["email"], { unique: true })
@Index("EMP_EMP_ID_PK", ["employeeId"], { unique: true })
@Index("EMP_JOB_IX", ["jobId"], {})
@Index("EMP_MANAGER_IX", ["managerId"], {})
@Index("EMP_NAME_IX", ["lastName", "firstName"], {})
@Entity("EMPLOYEES")
export class Employees {
  @Column("number", { name: "EMPLOYEE_ID", precision: 6, scale: 0 })
  employeeId: number;

  @Column("varchar2", { name: "FIRST_NAME", nullable: true, length: 20 })
  firstName: string | null;

  @Column("varchar2", { name: "LAST_NAME", length: 25 })
  lastName: string;

  @Column("varchar2", { primary: true, name: "EMAIL", length: 25 })
  email: string;

  @Column("varchar2", { name: "PHONE_NUMBER", nullable: true, length: 20 })
  phoneNumber: string | null;

  @Column("date", { name: "HIRE_DATE" })
  hireDate: Date;

  @Column("varchar2", { name: "JOB_ID", length: 10 })
  jobId: string;

  @Column("number", { name: "SALARY", nullable: true, precision: 8, scale: 2 })
  salary: number | null;

  @Column("number", {
    name: "COMMISSION_PCT",
    nullable: true,
    precision: 2,
    scale: 2,
  })
  commissionPct: number | null;

  @Column("number", {
    name: "MANAGER_ID",
    nullable: true,
    precision: 6,
    scale: 0,
  })
  managerId: number | null;

  @Column("number", {
    name: "DEPARTMENT_ID",
    nullable: true,
    precision: 4,
    scale: 0,
  })
  departmentId: number | null;

  @OneToMany(() => Departments, (departments) => departments.manager)
  departments: Departments[];

  @ManyToOne(() => Departments, (departments) => departments.employees)
  @JoinColumn([{ name: "DEPARTMENT_ID", referencedColumnName: "departmentId" }])
  department: Departments;

  @ManyToOne(() => Jobs, (jobs) => jobs.employees)
  @JoinColumn([{ name: "JOB_ID", referencedColumnName: "jobId" }])
  job: Jobs;

  @ManyToOne(() => Employees, (employees) => employees.employees)
  @JoinColumn([{ name: "MANAGER_ID", referencedColumnName: "employeeId" }])
  manager: Employees;

  @OneToMany(() => Employees, (employees) => employees.manager)
  employees: Employees[];

  @OneToMany(() => JobHistory, (jobHistory) => jobHistory.employee)
  jobHistories: JobHistory[];
}
