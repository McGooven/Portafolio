import { Column, Entity, Index } from "typeorm";

@Index("CARROUSEL_PK", ["idImg"], { unique: true })
@Entity("CARROUSEL")
export class Carrousel {
  @Column("varchar2", { name: "IMG", nullable: true, length: 300 })
  img: string | null;

  @Column("number", { primary: true, name: "ID_IMG" })
  idImg: number;
}
