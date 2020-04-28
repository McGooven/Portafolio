import {Request,Response} from 'express'
import { getRepository, getConnection } from "typeorm";

import { Usuario } from "../entities/Usuario"

export const getUsuarios = async (req: Request, res: Response): Promise<Response> => {
    const usuarios = await getConnection("default").getRepository(Usuario).find();
    console.log(usuarios);
    return res.json(usuarios);
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
