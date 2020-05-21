import {Request,Response} from 'express'
import fs from "fs";
import path from "path";
import { getRepository, getConnection, createQueryBuilder } from "typeorm";

import { Usuario } from "../entities/Usuario"
import { Centro } from "../entities/Centro"
import { Box } from "../entities/Box"
import { Region } from "../entities/Region"
import { EspInter } from "../entities/EspInter"
import { Comuna } from "../entities/Comuna"
import { Personal } from "../entities/Personal"
import { Profesion } from "../entities/Profesion"
import { Direccion } from "../entities/Direccion"
import { FichaPaciente } from "../entities/FichaPaciente"
import { Atencion } from '../entities/Atencion';
import { Carrousel } from "../entities/Carrousel";

//Manejo de Mantenedor de usuarios

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
        "WHEN \"us\".permisos = 4 THEN 'Medico' "+
        "ELSE 'Paciente' "+
        "END as Tipo"
    ])
    .where("\"us\".habilitado = :h and (\"per\".habilitado = :hp or \"fi\".habilitado= :hf)",{h:'S',hp:'S ',hf:'S'})
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
    .where("user.habilitado = :h",{h:"S"})
    .getMany();

    const query3 = await getRepository(Region)
    .createQueryBuilder('reg')
    .getMany();

    const query4 = await getRepository(Comuna)
    .createQueryBuilder('com')
    .getMany();

    const query5 = await getRepository(EspInter)
    .createQueryBuilder('cargos')
    .where("cargos.idEspecialidad not in (:id)",{id:4})
    .getMany();

    const query6 = await getRepository(Centro)
    .createQueryBuilder('centro')
    .getMany();

    result.push({
        "rows":query,
        "UsuariosObj":query2,
        "regiones":query3,
        "comunas":query4,
        "cargos":query5,
        "centro":query6
    });

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
    let user;
    console.log(JSON.stringify(req.body,null,3));
    if(req.body.fichaPacienteIdFicha){
        let region;
        let comuna;
        let centro;
        if(req.body.fichaPacienteIdFicha.centroIdCentro.idCentro){
            centro = req.body.fichaPacienteIdFicha.centroIdCentro as Centro;
        }
        if(req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.regionIdRegion.idRegion){
            region = req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.regionIdRegion as Region;
        }
        if(req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.idComuna) {
            comuna = req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna as Comuna;
        }

        let newDireccion = getRepository(Direccion).create(req.body.fichaPacienteIdFicha.direccionIdDireccion as Direccion);
        const direccion = await getRepository(Direccion).save(newDireccion);

        let newPaciente  = getRepository(FichaPaciente).create(req.body.fichaPacienteIdFicha as FichaPaciente);
        newPaciente.direccionIdDireccion = direccion;
        console.log(newPaciente);
        const paciente =await getRepository(FichaPaciente).save(newPaciente);

        let newUser      = getRepository(Usuario).create(req.body as Usuario);
        newUser.fichaPacienteIdFicha = paciente;
        const usuario = await getRepository(Usuario).save(newUser);
        user = usuario;
    }else{
        let region;
        let comuna;
        let cargo;
        let centro;
        if(req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion.idRegion){
            region = req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion as Region;
        }
        if(req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.idComuna) {
            comuna = req.body.personalIdPersonal.direccionIdDireccion.comunaComuna as Comuna;
        }
        if(req.body.personalIdPersonal.espInters[0].idEspecialidad){
            cargo = req.body.personalIdPersonal.espInters[0] as EspInter;
        }
        if(req.body.personalIdPersonal.centroIdCentro.idCentro){
            centro = req.body.personalIdPersonal.centroIdCentro as Centro;
        }

        try{
            let newProfesion = getRepository(Profesion).create(req.body.personalIdPersonal.profesions[0] as Profesion);
            const profesion = await getRepository(Profesion).save(newProfesion);
    
            let newDireccion = getRepository(Direccion).create(req.body.personalIdPersonal.direccionIdDireccion as Direccion);
            const direccion = await getRepository(Direccion).save(newDireccion);
    
            let newPersonal  = getRepository(Personal).create(req.body.personalIdPersonal as Personal);
            newPersonal.direccionIdDireccion = direccion;
            newPersonal.profesions[0] = profesion;
            newPersonal.espInters[0] = cargo;
            console.log(newPersonal);
            const personal =await getRepository(Personal).save(newPersonal);
    
            let newUser      = getRepository(Usuario).create(req.body as Usuario);
            newUser.personalIdPersonal = personal;
            const usuario = await getRepository(Usuario).save(newUser);
            user = usuario;
            //console.log(JSON.stringify(usuario,null,2));
        }catch(error){
            console.log(error.message);
        }
    }
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
        "WHEN \"us\".permisos = 4 THEN 'Medico' "+
        "ELSE 'Paciente' "+
        "END as Tipo"
    ])
    .where("\"us\".habilitado = :h and (\"per\".habilitado = :hp or \"fi\".habilitado= :hf)",{h:'S',hp:'S ',hf:'S'})
    .getRawMany();


    return res.json([{"newUser":user},[{"rows":query}]]);
}

export const updateUsuario = async (req: Request, res: Response): Promise<Response> => {
    console.log(JSON.stringify(req.body,null,2));
    const usuarioTemp= await getRepository(Usuario).findOne(req.body.idUsuario);
    if(usuarioTemp){
        await getRepository(Usuario).merge(usuarioTemp,req.body);
        const usuario = await getRepository(Usuario).save(usuarioTemp);
        /*
        const usuario= await getConnection()
        .createQueryBuilder()
        .update(Usuario)
        .set(req.body)
        .where("idUsuario = :id", { id: req.body.idUsuario})
        .execute();
        */
        if(req.body.fichaPacienteIdFicha != null){
            const pacienteTemp= await getRepository(FichaPaciente).findOne(req.body.fichaPacienteIdFicha.idFicha);
            if(pacienteTemp){
                await getRepository(FichaPaciente).merge(pacienteTemp,req.body.fichaPacienteIdFicha);
                const paciente = await getRepository(FichaPaciente).save(pacienteTemp);

                /*
                const paciente  = await getConnection()
                .createQueryBuilder()
                .update(FichaPaciente)
                .set(req.body.fichaPacienteIdFicha)
                .where("idFicha = :id", { id: req.body.fichaPacienteIdFicha.idFicha})
                .execute();
                */
            }
            
            const centroTemp= await getRepository(Centro).findOne(req.body.fichaPacienteIdFicha.centroIdCentro.idCentro);
            if(centroTemp){
                await getRepository(Centro).merge(centroTemp,req.body.fichaPacienteIdFicha.centroIdCentro);
                const centro = await getRepository(Centro).save(centroTemp);
                /*
                const centro  = await getConnection()
                .createQueryBuilder()
                .update(Centro)
                .set(req.body.fichaPacienteIdFicha.centroIdCentro)
                .where("idCentro = :id", { id: req.body.fichaPacienteIdFicha.centroIdCentro.idCentro})
                .execute();
                */
            }

            const direccionTemp= await getRepository(Direccion).findOne(req.body.fichaPacienteIdFicha.direccionIdDireccion.idDireccion);
            if(direccionTemp){
                await getRepository(Direccion).merge(direccionTemp,req.body.fichaPacienteIdFicha.direccionIdDireccion);
                const direccion = await getRepository(Direccion).save(direccionTemp);
                /* 
                const direccion = await getConnection()
                .createQueryBuilder()
                .update(Direccion)
                .set(req.body.fichaPacienteIdFicha.direccionIdDireccion)
                .where("idDireccion = :id", { id: req.body.fichaPacienteIdFicha.direccionIdDireccion.idDireccion})
                .execute();
                */
            }

            const comunaTemp= await getRepository(Comuna).findOne(req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.idComuna);
            if(comunaTemp){
                await getRepository(Comuna).merge(comunaTemp,req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna);
                const comuna = await getRepository(Comuna).save(comunaTemp);
                /*            
                const comuna    = await getConnection()
                .createQueryBuilder()
                .update(Comuna)
                .set(req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna)
                .where("idComuna = :id", { id: req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.idComuna})
                .execute(); 
                */
            }

            const regionTemp= await getRepository(Region).findOne(req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.regionIdRegion.idRegion);
            if(regionTemp){
                await getRepository(Region).merge(regionTemp,req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.regionIdRegion);
                const comuna = await getRepository(Region).save(regionTemp);
                /*
                const region    = await getConnection()
                .createQueryBuilder()
                .update(Region)
                .set(req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.regionIdRegion)
                .where("idRegion = :id", { id: req.body.fichaPacienteIdFicha.direccionIdDireccion.comunaComuna.regionIdRegion.idRegion})
                .execute();
                */
            }

        }else{
            const personalTemp= await getRepository(Personal).findOne(req.body.personalIdPersonal.idPersonal);
            if (personalTemp){
                await getRepository(Personal).merge(personalTemp,req.body.personalIdPersonal);
                const personal = await getRepository(Personal).save(personalTemp);
                /*const personal  = await getConnection()
                .createQueryBuilder()
                .update(Personal)
                .set(temppersonal)
                .where("idPersonal = :id", { id: req.body.personalIdPersonal.idPersonal})
                .execute();*/
            }
            
            const centroTemp= await getRepository(Centro).findOne(req.body.personalIdPersonal.centroIdCentro.idCentro);
            if(centroTemp){
                await getRepository(Centro).merge(centroTemp,req.body.personalIdPersonal.centroIdCentro);
                const centro = await getRepository(Centro).save(centroTemp);
                /*
                const centro  = await getConnection()
                .createQueryBuilder()
                .update(Centro)
                .set(req.body.personalIdPersonal.centroIdCentro)
                .where("idCentro = :id", { id: req.body.personalIdPersonal.centroIdCentro.idCentro})
                .execute();
                */
            }

            const direccionTemp= await getRepository(Direccion).findOne(req.body.personalIdPersonal.direccionIdDireccion.idDireccion);
            if(direccionTemp){
                await getRepository(Direccion).merge(direccionTemp,req.body.personalIdPersonal.direccionIdDireccion);
                const direccion = await getRepository(Direccion).save(direccionTemp);
                /* 
                const direccion = await getConnection()
                .createQueryBuilder()
                .update(Direccion)
                .set(req.body.personalIdPersonal.direccionIdDireccion)
                .where("idDireccion = :id", { id: req.body.personalIdPersonal.direccionIdDireccion.idDireccion})
                .execute();
                */
            }

            const comunaTemp= await getRepository(Comuna).findOne(req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.idComuna);
            if(comunaTemp){
                console.log(comunaTemp);
                await getRepository(Comuna).merge(comunaTemp,req.body.personalIdPersonal.direccionIdDireccion.comunaComuna);
                const comuna = await getRepository(Comuna).save(comunaTemp);
                /*            
                const comuna    = await getConnection()
                .createQueryBuilder()
                .update(Comuna)
                .set(req.body.personalIdPersonal.direccionIdDireccion.comunaComuna)
                .where("idComuna = :id", { id: req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.idComuna})
                .execute(); 
                */
            }

            const regionTemp= await getRepository(Region).findOne(req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion.idRegion);
            if(regionTemp){
                await getRepository(Region).merge(regionTemp,req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion);
                const comuna = await getRepository(Region).save(regionTemp);
                /*
                const region    = await getConnection()
                .createQueryBuilder()
                .update(Region)
                .set(req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion)
                .where("idRegion = :id", { id: req.body.personalIdPersonal.direccionIdDireccion.comunaComuna.regionIdRegion.idRegion})
                .execute();
                */
            }

            req.body.personalIdPersonal.profesions.forEach(async element => {
                const profesionTemp= await getRepository(Profesion).findOne(element.idProfesion);
                if(profesionTemp){
                    await getRepository(Profesion).merge(profesionTemp,element);
                    const comuna = await getRepository(Profesion).save(profesionTemp);
                }
                /*
                const profesion  = await getConnection()
                .createQueryBuilder()
                .update(Profesion)
                .set(req.body.personalIdPersonal.profesions)
                .where("idProfesion = :id", { id: element.idProfesion})
                .execute();
                */
            });

            req.body.personalIdPersonal.espInters.forEach(async element => {
                const espInterTemp= await getRepository(EspInter).findOne(element.idProfesion);
                if(espInterTemp){
                    await getRepository(EspInter).merge(espInterTemp,element);
                    const comuna = await getRepository(EspInter).save(espInterTemp);
                }
                /*
                console.log(element)
                const espInters  = await getConnection()
                .createQueryBuilder()
                .update(EspInter)
                .set(req.body.personalIdPersonal.espInters)
                .where("idEspecialidad = :id", { id: element.idEspecialidad})
                .execute();
                */
            });

        }   

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
            "WHEN \"us\".permisos = 4 THEN 'Medico' "+
            "ELSE 'Paciente' "+
            "END as Tipo"
        ])
        .where("\"us\".habilitado = :h and (\"per\".habilitado = :hp or \"fi\".habilitado= :hf)",{h:'S',hp:'S ',hf:'S'})
        .getRawMany();

        return res.json([usuario,[{"rows":query}]]);
    }else{
        return res.status(404).json([{msg:'usuario no encontrado'}]);
    }

}

export const deleteUsuario = async (req: Request, res: Response): Promise<Response> => {
    console.log(JSON.stringify(req.body,null,2));
    const usuarioTemp= await getRepository(Usuario).findOne(req.body.idUsuario);
    let result = [] as any;
    if(usuarioTemp){
        await getRepository(Usuario).merge(usuarioTemp,req.body);
        const usuario = await getRepository(Usuario).save(usuarioTemp);
        result.push(usuario);
    }

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
        "WHEN \"us\".permisos = 4 THEN 'Medico' "+
        "ELSE 'Paciente' "+
        "END as Tipo"
    ])
    .where("\"us\".habilitado = :h and (\"per\".habilitado = :hp or \"fi\".habilitado= :hf)",{h:'S',hp:'S ',hf:'S'})
    .getRawMany();

    result.push([{"rows":query}]);
    console.log(result);
    return res.json(result);
}

//Atenciones
export const getAtenciones = async (req: Request, res: Response): Promise<Response> => {
    var result=[] as any;
    const query = await getRepository(Atencion)
    .createQueryBuilder('ate')
    .leftJoinAndSelect('ate.atenInsus','cant')
    .leftJoinAndSelect('cant.insumoIdInsumo2','insu')
    .getMany();

    result.push({atenciones:query});

    console.log(result);
    return res.json(result);
}

//Manejo del Mantenedor de Centros

export const getcentros = async (req: Request, res: Response): Promise<Response> => {
    var result=[] as any;
    const query = await getRepository(Centro)
    .createQueryBuilder('c')
    .leftJoinAndSelect('c.direccionIdDireccion','d')
    .leftJoinAndSelect('d.comunaComuna','com')
    .leftJoinAndSelect('com.regionIdRegion','reg')
    .leftJoinAndSelect('c.boxes','box')
    .select()
    .getMany();

    result.push({centro:query});

    console.log(result);
    return res.json(result);
}

//Web Page
export const getCarrousel = async(req: Request, res: Response): Promise<Response> =>{
    const imagen = await getRepository(Carrousel).findOne({idImg:req.params.nombre as any});
    if (imagen){
        res.sendFile(path.join(__dirname,'../../Media/'+imagen.img));
    }
    return res;
}