import { Column, Entity, Index, OneToMany } from "typeorm";
import { Employees } from "./Employees";
import { JobHistory } from "./JobHistory";

@Index("JOB_ID_PK", ["jobId"], { unique: true })
@Entity("JOBS")
export class Jobs {
  @Column("varchar2", { primary: true, name: "JOB_ID", length: 10 })
  jobId: string;

  @Column("varchar2", { name: "JOB_TITLE", length: 35 })
  jobTitle: string;

  @Column("number", {
    name: "MIN_SALARY",
    nullable: true,
    precision: 6,
    scale: 0,
  })
  minSalary: number | null;

  @Column("number", {
    name: "MAX_SALARY",
    nullable: true,
    precision: 6,
    scale: 0,
  })
  maxSalary: number | null;

  @OneToMany(() => Employees, (employees) => employees.job)
  employees: Employees[];

  @OneToMany(() => JobHistory, (jobHistory) => jobHistory.job)
  jobHistories: JobHistory[];
}
