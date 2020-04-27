import { Column, Entity, Index, JoinColumn, ManyToOne } from "typeorm";
import { Departments } from "./Departments";
import { Employees } from "./Employees";
import { Jobs } from "./Jobs";

@Index("JHIST_DEPARTMENT_IX", ["departmentId"], {})
@Index("JHIST_EMP_ID_ST_DATE_PK", ["employeeId", "startDate"], { unique: true })
@Index("JHIST_EMPLOYEE_IX", ["employeeId"], {})
@Index("JHIST_JOB_IX", ["jobId"], {})
@Entity("JOB_HISTORY")
export class JobHistory {
  @Column("number", {
    primary: true,
    name: "EMPLOYEE_ID",
    precision: 6,
    scale: 0,
  })
  employeeId: number;

  @Column("date", { primary: true, name: "START_DATE" })
  startDate: Date;

  @Column("date", { name: "END_DATE" })
  endDate: Date;

  @Column("varchar2", { name: "JOB_ID", length: 10 })
  jobId: string;

  @Column("number", {
    name: "DEPARTMENT_ID",
    nullable: true,
    precision: 4,
    scale: 0,
  })
  departmentId: number | null;

  @ManyToOne(() => Departments, (departments) => departments.jobHistories)
  @JoinColumn([{ name: "DEPARTMENT_ID", referencedColumnName: "departmentId" }])
  department: Departments;

  @ManyToOne(() => Employees, (employees) => employees.jobHistories)
  @JoinColumn([{ name: "EMPLOYEE_ID", referencedColumnName: "employeeId" }])
  employee: Employees;

  @ManyToOne(() => Jobs, (jobs) => jobs.jobHistories)
  @JoinColumn([{ name: "JOB_ID", referencedColumnName: "jobId" }])
  job: Jobs;
}
