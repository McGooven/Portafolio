import { Column, Entity, Index, OneToMany,PrimaryGeneratedColumn } from "typeorm";
import { Atencion } from "./Atencion";

@Index("TIPO_SESION_PK", ["idTpSn"], { unique: true })
@Entity("TIPO_SESION")
export class TipoSesion {
  @Column("varchar2", { name: "NOMBRE_TP_SN", length: 50 })
  nombreTpSn: string;

  @PrimaryGeneratedColumn({ name: "ID_TP_SN" })
  idTpSn: number;

  @OneToMany(() => Atencion, (atencion) => atencion.tipoSesionIdTpSn)
  atencions: Atencion[];
}
