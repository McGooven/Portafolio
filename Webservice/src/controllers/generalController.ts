import {Request,Response} from 'express'
import { getRepository, getConnection } from "typeorm";

import { Usuario } from "../entities/Usuario"

export const getUsuarios = async (req: Request, res: Response): Promise<Response> => {
    var result=[] as any;
    const query= await getConnection("default")
    .createQueryBuilder()
    .from(Usuario,"us")
    .leftJoinAndSelect("us.personalIdPersonal","per")
    .leftJoinAndSelect("us.fichaPacienteIdFicha","fi")
    .select([
        "CASE WHEN \"us\".personal_Id_Personal is not null " +
        "THEN \"per\".rut_Personal "+
        "ELSE \"fi\".rut_Paciente "+
        "END as rut",
        "CASE WHEN \"us\".personal_Id_Personal is not null " +
        "THEN \"per\".pnombre||' '||\"per\".snombre||' '||\"per\".papellido||' '||\"per\".sapellido "+
        "ELSE \"fi\".pnombre||' '||\"fi\".snombre||' '||\"fi\".papellido||' '||\"fi\".sapellido "+
        "END as Nombre",
        "CASE WHEN \"us\".permisos = 1 THEN 'Administrador'"+
        "WHEN \"us\".permisos = 2 THEN 'Administrativo' "+
        "WHEN \"us\".permisos = 3 THEN 'Enfermero' "+
        "WHEN \"us\".permisos = 5 THEN 'Medico' "+
        "ELSE 'Paciente' "+
        "END as Tipo"
    ])
    .getRawMany();

    const query2=await getRepository(Usuario)
    .createQueryBuilder('user')
    .leftJoinAndSelect('user.personalIdPersonal','per')
    .leftJoinAndSelect('user.fichaPacienteIdFicha','fi')
    .leftJoinAndSelect('per.profesions','prof')
    .leftJoinAndSelect('per.espInters','cargos')
    .leftJoinAndSelect('per.centroIdCentro','centPer')
    .leftJoinAndSelect('fi.centroIdCentro','centFi')
    .leftJoinAndSelect('per.direccionIdDireccion','dirPer')
    .leftJoinAndSelect('fi.direccionIdDireccion','dirFi')
    .leftJoinAndSelect('dirPer.comunaComuna','comPer')
    .leftJoinAndSelect('dirFi.comunaComuna','comFi')
    .leftJoinAndSelect('comPer.regionIdRegion','regPer')
    .leftJoinAndSelect('comFi.regionIdRegion','regFi')
    .select()
    .getMany();


    result.push({"rows":query,"UsuariosObj":query2});

    console.log(result);
    return res.json(result);
}

export const getUsuario = async (req: Request, res: Response): Promise<Response> => {
    const usuario = await getRepository(Usuario).find({where : {idUsuario:req.params.idUsuario}});
    console.log(req.params);
    return res.json(usuario);
}

export const findAccount = async (req: Request, res: Response): Promise<Response> => {
    const usuario = await getRepository(Usuario)
    .createQueryBuilder("user")
    .where("user.correo = :correo",{"correo":req.body.correo})
    .andWhere("user.password = :pass",{"pass":req.body.password})
    .getMany();
    console.log(usuario);
    return res.json(usuario);
}

export const crearUsuario = async (req: Request, res: Response): Promise<Response> => {
    const usuario = await getRepository(Usuario).create(req.body);
    const result = await getRepository(Usuario).save(usuario);
    return res.json(result);
}

export const updateUsuario = async (req: Request, res: Response): Promise<Response> => {
    const usuario = await getRepository(Usuario).findOne(req.params.idUsuario);
    if (usuario) {
        getRepository(Usuario).merge(usuario,req.body);
        const result = await getRepository(Usuario).save(usuario);
        return res.json(result);
    } else {
        return res.status(404).json({msg:'usuario no encontrado'});
    }
}

export const deleteUsuario = async (req: Request, res: Response): Promise<Response> => {
    const usuario = await getRepository(Usuario).delete(req.params.idUsuario);
    return res.json(usuario);
}
