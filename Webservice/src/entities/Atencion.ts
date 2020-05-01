import {
  Column,
  Entity,
  Index,
  JoinColumn,
  JoinTable,
  ManyToMany,
  ManyToOne,
  OneToMany,
} from "typeorm";
import { Box } from "./Box";
import { TipoSesion } from "./TipoSesion";
import { AtenInsu } from "./AtenInsu";
import { FichaPaciente } from "./FichaPaciente";
import { PersoAten } from "./PersoAten";

@Index("ATENCION_PK", ["idAtencion"], { unique: true })
@Entity("ATENCION")
export class Atencion {
  @Column("date", { name: "FECHA_INGRESO" })
  fechaIngreso: Date;

  @Column("date", { name: "FECHA_TERMINO", nullable: true })
  fechaTermino: Date | null;

  @Column("number", { primary: true, name: "ID_ATENCION" })
  idAtencion: number;

  @Column("number", { name: "SITUACION" })
  situacion: number;

  @ManyToOne(() => Box, (box) => box.atencions)
  @JoinColumn([{ name: "BOX_ID_BOX", referencedColumnName: "idBox" }])
  boxIdBox: Box;

  @ManyToOne(() => TipoSesion, (tipoSesion) => tipoSesion.atencions)
  @JoinColumn([
    { name: "TIPO_SESION_ID_TP_SN", referencedColumnName: "idTpSn" },
  ])
  tipoSesionIdTpSn: TipoSesion;

  @OneToMany(() => AtenInsu, (atenInsu) => atenInsu.atencionIdAtencion2)
  atenInsus: AtenInsu[];

  @ManyToMany(() => FichaPaciente, (fichaPaciente) => fichaPaciente.atencions)
  @JoinTable({
    name: "ATEN_PAC",
    joinColumns: [
      { name: "ATENCION_ID_ATENCION", referencedColumnName: "idAtencion" },
    ],
    inverseJoinColumns: [
      { name: "FICHA_PACIENTE_ID_FICHA", referencedColumnName: "idFicha" },
    ],
  })
  fichaPacientes: FichaPaciente[];

  @OneToMany(() => PersoAten, (persoAten) => persoAten.atencionIdAtencion)
  persoAtens: PersoAten[];
}
