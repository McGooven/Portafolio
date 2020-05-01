import { Column, Entity, Index, JoinColumn, ManyToOne } from "typeorm";
import { Personal } from "./Personal";

@Index("PROFESION_PK", ["idProfesion"], { unique: true })
@Entity("PROFESION")
export class Profesion {
  @Column("number", { primary: true, name: "ID_PROFESION" })
  idProfesion: number;

  @Column("blob", { name: "CERTIFICADO", nullable: true })
  certificado: Buffer | null;

  @Column("varchar2", { name: "TITULO", length: 60 })
  titulo: string;

  @Column("date", { name: "FECHA_EGRESO" })
  fechaEgreso: Date;

  @Column("varchar2", { name: "CASA_ESTUDIO", length: 50 })
  casaEstudio: string;

  @ManyToOne(() => Personal, (personal) => personal.profesions)
  @JoinColumn([
    { name: "PERSONAL_ID_PERSONAL", referencedColumnName: "idPersonal" },
  ])
  personalIdPersonal: Personal;
}
