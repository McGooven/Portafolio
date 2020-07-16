import {Request,Response} from 'express'
import fs from "fs";
import path from "path";
import Telegraf, { Context } from "telegraf";
import commandParts  from "telegraf-command-parts";
import { getRepository, getConnection, createQueryBuilder, Any } from "typeorm";
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
import { TipoSesion } from '../entities/TipoSesion';
import { Console } from 'console';
import { Insumo } from '../entities/Insumo';
import { AtenInsu } from '../entities/AtenInsu';
import { PersoAten } from '../entities/PersoAten';

const bot = new Telegraf('1111109689:AAHodg1WH27B0vfs4F3tctOM11BeWRRcy9Y');
bot.use(commandParts());
bot.command('ok',async ctx => {
    const c = ctx as any;
    const argumentos = parseInt(c.contextState.command.args,10);
    if(argumentos){
        const updateBox = await getConnection()
        .createQueryBuilder()
        .update(Box)
        .set({estado:'Disponible'})
        .where("idBox = :id", { id: argumentos})
        .execute();
        ctx.reply('Estado del Box actualizado, gracias.')
    }else{
        ctx.reply('el box nro° '+argumentos+' no existe, diganos un nro° correcto por favor.')
    }
})
bot.launch();

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
    .leftJoinAndSelect("user.personalIdPersonal","per")
    .leftJoinAndSelect("per.centroIdCentro","cent")
    .select([
        'user.idUsuario as \"idUsuario\"',
        'user.habilitado as \"habilitado\"',
        'user.permisos as \"permisos\"',
        'cent.idCentro as \"idCentro\"'
    ])
    .where("user.correo = :correo",{"correo":req.body.correo})
    .andWhere("user.password = :pass",{"pass":req.body.password})
    .getRawMany();
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
    const query1 = await getRepository(Centro)
    .createQueryBuilder()
    .getMany();

    result.push({centro:query, listView:query1});

    console.log(result);
    return res.json(result);
}

//ATENCIONES Y AGENDAMIENTO.
export const getBoxesState = async(req: Request, res:Response): Promise<Response> =>{
    var result = [] as any;
    
    var idCentro = req.body.idCentro;

    const query = await getRepository(Box)
    .createQueryBuilder('bx')
    .leftJoinAndSelect('bx.centroIdCentro','cent')
    .select()
    .where('cent.idCentro = :idcent',{idcent : idCentro})
    .andWhere('bx.habilitada like :h',{h:'%S%'})
    .getMany();

    result.push({boxes:query});

    console.log(result);
    return res.json(result);
}

export const getPlanification = async(req: Request, res:Response): Promise<Response> =>{
    console.log(req.body)

    const query = await getRepository(Atencion)
    .createQueryBuilder('aten')
    .leftJoinAndSelect('aten.boxIdBox','box')
    .leftJoinAndSelect('aten.tipoSesionIdTpSn','tpSesi')
    .select([
        "box.idBox as \"idBox\"",
        "aten.idAtencion as \"idAtencion\"",
        "tpSesi.idTpSn as \"tipo\"",
        "to_char(aten.fechaIngreso,'dd-mm-yyyy hh24:mi:ss') as \"fechaIngreso\"",
        "to_char(aten.fechaTermino,'dd-mm-yyyy hh24:mi:ss') as \"fechaTermino\""
    ])
    .where("box.idBox = :idBox",{idBox : req.body.idBox})
    .andWhere("aten.situacion = :A",{A : 0})
    .getRawMany();

    console.log(query);
    return res.json(query);
}

export const peticionEnfermera = async(req: Request, res: Response): Promise<Response> =>{
    var state = '';
    var msg = '';
    var conserje = false;
    var requestOK= true;
    switch (req.body.accion) {
        case 'Iniciar':
            state = 'Ocupado'

            break;
        case 'Terminar':
            state = 'Mantencion'
            msg = "-- Solicitud de mantención --" +"\n"+
                            "nro. Box: "+ req.body.idBox +"\n"+
                            "id notificante: "+ req.body.idUsuario +"\n"+
                            "Sesion anterior: "+ req.body.nombre +"\n"+
                            "\n"+
                            "Favor de enviar personal lo mas pronto posible." +"\n"+
                            "Atte. el Bot :)";
            conserje = true;

            const terminar = await getConnection()
            .createQueryBuilder()
            .update(Atencion)
            .set({situacion:1})
            .where("idAtencion = :id", { id: req.body.idAtencion })
            .execute();

            break;
        case 'Suspender':
            state = 'Mantencion'
            msg = "-- Solicitud de mantención --" +"\n"+
                            "nro. Box: "+ req.body.idBox +"\n"+
                            "id notificante: "+ req.body.idUsuario +"\n"+
                            "Sesion anterior: "+ req.body.nombre +"\n"+
                            "\n"+
                            "Favor de enviar personal lo mas pronto posible." +"\n"+
                            "Atte. el Bot :)";
            conserje = true;

            const suspender = await getConnection()
            .createQueryBuilder()
            .update(Atencion)
            .set({situacion:2})
            .where("idAtencion = :id", { id: req.body.idAtencion })
            .execute();

            break;
        default:
            if(req.body.id == 'btnIniSesion'){
                state = 'Ocupado'

            }else if(req.body.id == 'btnTerSesion'){
                state = 'Mantencion'
                conserje = true;
                msg = "-- Solicitud de mantención --" +"\n"+
                                "nro. Box: "+ req.body.idBox +"\n"+
                                "id notificante: "+ req.body.idUsuario +"\n"+
                                "Sesion anterior: "+ req.body.nombre +"\n"+
                                "Resolución: Terminada" +"\n"+
                                "\n"+
                                "Favor de enviar personal lo mas pronto posible." +"\n"+
                                "Atte. el Bot :)";

                const terminar = await getConnection()
                .createQueryBuilder()
                .update(Atencion)
                .set({situacion:1})
                .where("idAtencion = :id", { id: req.body.idAtencion })
                .execute();

            }else if (req.body.id == 'btnSuspSesion'){
                state = 'Mantencion'
                conserje = true;
                msg = "-- Solicitud de mantención --" +"\n"+
                                "nro. Box: "+ req.body.idBox +"\n"+
                                "id notificante: "+ req.body.idUsuario +"\n"+
                                "Sesion anterior: "+ req.body.nombre +"\n"+
                                "Resolución: Suspendida" +"\n"+
                                "\n"+
                                "Favor de enviar personal lo mas pronto posible." +"\n"+
                                "Atte. el Bot :)";

                const suspender = await getConnection()
                .createQueryBuilder()
                .update(Atencion)
                .set({situacion:2})
                .where("idAtencion = :id", { id: req.body.idAtencion })
                .execute();

            }else {
                console.log('algo va mal con los botones.')
                requestOK = false;
            }

            break;
    }

    if (requestOK) {
        const updateBox = await getConnection()
        .createQueryBuilder()
        .update(Box)
        .set({estado:state})
        .where("idBox = :id", { id: req.body.idBox })
        .execute();

        if (conserje) {
            bot.telegram.sendMessage('@LimpiezaYSatinizacion',msg);
        }
    }

    return res.json({});
}

export const getWeekPlanification = async(req: Request, res:Response): Promise<Response> =>{
    console.log(req.body)

    const query = await getRepository(Atencion)
    .createQueryBuilder('aten')
    .leftJoinAndSelect('aten.boxIdBox','box')
    .leftJoinAndSelect('aten.tipoSesionIdTpSn','tpSesi')
    .leftJoinAndSelect('box.centroIdCentro','cent')
    .select([
        "box.idBox as \"idBox\"",
        "aten.idAtencion as \"idAtencion\"",
        "tpSesi.idTpSn as \"tipo\"",
        "to_char(aten.fechaIngreso,'dd-mm-yyyy hh24:mi:ss') as \"fechaIngreso\"",
        "to_char(aten.fechaTermino,'dd-mm-yyyy hh24:mi:ss') as \"fechaTermino\""
    ])
    .where("aten.situacion = :A",{A : 0})
    .andWhere("cent.idCentro = :B",{B : req.body.centroId})
    .getRawMany();

    console.log(query);
    return res.json(query);
}

export const getTiposSesion = async(req: Request, res: Response): Promise<Response> =>{
    const query = await getRepository(TipoSesion)
    .createQueryBuilder('tps')
    .select([
        'tps.idTpSn as \"idTpSn\"',
        'tps.nombreTpSn as \"nombreTpSn\"'
    ])
    .getRawMany();
    return res.json(query);
}

export const getPacandPersAvailables = async(req: Request, res: Response): Promise<Response> =>{
    var d=req.body.fechaInicio;
    var d1=d.split(" ");
    var date=d1[0].split("-");
    var time=d1[1].split(":");
    var dd=date[0];
    var MM=date[1]-1;
    var yyyy=date[2];
    var hh=time[0];
    var mi=time[1];
    var ss=time[2];
    var fromdt= new Date(yyyy,MM,dd,hh,mi,ss);

    var fechaIngreso = fromdt;
    var idTipoSesion = req.body.idTipoSesion;
    var idCentro = req.body.centroId;
    let result=[] as any;

    const query = await getRepository(Personal)
    .createQueryBuilder('per')
    .leftJoinAndSelect('per.persoAtens','pa')
    .leftJoinAndSelect('per.centroIdCentro','cent')
    .leftJoinAndSelect('pa.atencionIdAtencion','aten')
    .leftJoinAndSelect('per.espInters','esp')
    .select([
        "DISTINCT \"per\".pnombre||' '||\"per\".snombre||' '||\"per\".papellido||' '||\"per\".sapellido as \"nombre\"",
        "per.rutPersonal as \"rut\""
    ])
    .where("(aten.situacion = :A or aten.situacion is null)",{A : 0})
    .andWhere("esp.idEspecialidad = :idesp",{idesp : idTipoSesion})
    .andWhere("(aten.fechaIngreso < TO_DATE(:fi,'dd/mm/yyyy hh24:mi:ss') and aten.fechaTermino < TO_DATE(:fi,'dd/mm/yyyy hh24:mi:ss'))",
                {fi :datetoddMMyyyy_HHmiss(fechaIngreso)}
    )
    .andWhere('cent.idCentro = :idcent',{idcent : idCentro})
    .getRawMany();

    const query1 = await getRepository(FichaPaciente)
    .createQueryBuilder('pac')
    .leftJoinAndSelect('pac.centroIdCentro','cent')
    .select([
        "\"pac\".pnombre||' '||\"pac\".snombre||' '||\"pac\".papellido||' '||\"pac\".sapellido as \"nombre\"",
        "pac.rutPaciente as \"rut\""
    ])
    .where('cent.idCentro = :idcent',{idcent : idCentro})
    .getRawMany();

    result.push({medicos:query,pacientes:query1});

    return res.json(result);
}

export const saveAtencion = async(req: Request, res: Response): Promise<Response> =>{
    console.log(req.body);
    let body= req.body.resultados[0] as Atencion;
    var fechaIngreso = sumarHoras(stringToDate(req.body.resultados[0].fechaIngreso),0);
    var fechaTermino;
    var idTipoSesion=req.body.resultados[0].tipoSesionIdTpSn as TipoSesion;
    // var idCentro=req.body.resultados[0].boxIdBox.idCentro;
    // var hrsExtraBool=req.body.checkExtra;
    var rutPersonal = req.body.resultados[1].rutPersonales;
    var rutPaciente = req.body.resultados[1].rutPaciente;
    let insumos;

    if(req.body.fechaTermino === undefined){
        if(idTipoSesion.idTpSn == 1){
            //kinesiología
            fechaTermino = datetoddMMyyyy_HHmissGuiones(sumarHoras(fechaIngreso,2));
        }else{
            //Fonoaudiología
            fechaTermino = datetoddMMyyyy_HHmissGuiones(sumarHoras(fechaIngreso,1));
        }
    }else{
        fechaTermino = datetoddMMyyyy_HHmissGuiones(stringToDate(req.body.fechaTermino));
    }

    var pacientes= []as any;
    pacientes= getRepository(FichaPaciente)
    .createQueryBuilder('pac')
    .select()
    .where('pac.rutPaciente like :rut',{rut : '%'+rutPaciente+'%'})
    .getMany();

    body.fechaTermino=fechaTermino;
    const atencion= await getConnection('default')
        .createQueryBuilder()
        .insert()
        .into(Atencion)
        .values({
            fechaIngreso: () => "to_date(to_char('" + body.fechaIngreso + "'), 'dd-MM-yyyy hh24:mi:ss')",
            fechaTermino: () => "to_date(to_char('" + body.fechaTermino + "'), 'dd-MM-yyyy hh24:mi:ss')",
            situacion: body.situacion,
            boxIdBox: body.boxIdBox,
            tipoSesionIdTpSn: body.tipoSesionIdTpSn,
            fichaPacientes: body.fichaPacientes
        })
        .execute();
    // let newAtencion = await getRepository(Atencion).create(body as Atencion);
    // newAtencion.fichaPacientes = pacientes;

    console.log(atencion);
    //const atencion = await getRepository(Atencion).save(newAtencion);

    
    var personales= []as any;
    personales= await getRepository(Personal)
    .createQueryBuilder('per')
    .select()
    .where('per.rutPersonal IN (:...ruts)',{ruts : rutPersonal})
    .getMany();
    for(const element of personales){
        let personal = element as Personal;
        const personalq=await getConnection('default')
        .createQueryBuilder()
        .insert()
        .into(PersoAten)
        .values({ 
            idPersoAten:undefined,
            atencionIdAtencion : atencion.identifiers[0].idAtencion,
            personalIdPersonal: personal
        })
        .execute();
    }

    console.log(atencion.identifiers[0].idAtencion);
    if(idTipoSesion.idTpSn == 1){
        //kinesiología
        insumos = await getRepository(Insumo).createQueryBuilder('insu')
        .select()
        .where('insu.idInsumo = :id',{id:1})
        .getMany();
        console.log(insumos);
        for(const element of insumos){
            let insumo =element as Insumo;
            let cantidad;
            if (insumo.idInsumo = 1) {
                cantidad=5;
            } else {
                cantidad = 0;
            }

            const atenInsu= getConnection('default')
            .createQueryBuilder()
            .insert()
            .into(AtenInsu)
            .values({ 
                atencionIdAtencion : atencion.identifiers[0].idAtencion,
                cantidad : cantidad,
                insumoIdInsumo : insumo.idInsumo 
            })
            .execute();
        }

    }else if(idTipoSesion.idTpSn == 3 ){
        insumos = await getRepository(Insumo).createQueryBuilder('insu')
        .select()
        .where('insu.idInsumo = :id',{id:3})
        .getMany();

        console.log(insumos);
        //Fonoaudiología
        for(const element of insumos){
            let insumo =element as Insumo;
            let cantidad;
            if (insumo.idInsumo = 3) {
                cantidad=2;
            } else {
                cantidad = 0;
            }

            const atenInsu= await getConnection('default')
            .createQueryBuilder()
            .insert()
            .into(AtenInsu)
            .values({ 
                atencionIdAtencion : atencion.identifiers[0].idAtencion,
                cantidad : cantidad,
                insumoIdInsumo : insumo.idInsumo 
            })
            .execute();
        }

    }
    


    return res.json({mensaje:'probando...'});

}

function sumarHoras(fecha : Date, horas : number) {
    var date = new Date(fecha);
    date.setHours(fecha.getHours() + horas);
    return date;
}
function datetoddMMyyyy_HHmiss(fecha: Date){
    var day = fecha.getDate() ;
    var month = fecha.getMonth()+1;
    var year = fecha.getFullYear();
    var hours = fecha.getHours();
    var minutes = fecha.getMinutes();
    var seconds = fecha.getSeconds();

    var formated= (day<=9 ? '0' + day : day) + '/' +(month<=9 ? '0' + month : month) +'/'+year+' '+(hours<=9 ? '0' + hours : hours)+':'+(minutes<=9 ? '0' + minutes : minutes)+':'+(seconds<=9 ? '0' + seconds : seconds);
    return formated;
}
function datetoddMMyyyy_HHmissGuiones(fecha:Date) {
    var day = fecha.getDate() ;
    var month = fecha.getMonth()+1;
    var year = fecha.getFullYear();
    var hours = fecha.getHours();
    var minutes = fecha.getMinutes();
    var seconds = fecha.getSeconds();

    var formated= (day<=9 ? '0' + day : day) + '-' +(month<=9 ? '0' + month : month) +'-'+year+' '+(hours<=9 ? '0' + hours : hours)+':'+(minutes<=9 ? '0' + minutes : minutes)+':'+(seconds<=9 ? '0' + seconds : seconds);
    return formated;
}

function stringToDate(param) {
    console.log(param);
    var d=param;
    var d1=d.split(" ");
    var date=d1[0].split("-");
    var time=d1[1].split(":");
    var dd=date[0];
    var MM=date[1]-1;
    var yyyy=date[2];
    var hh=time[0];
    var mi=time[1];
    var ss=time[2];

    return new Date(yyyy,MM,dd,hh,mi,ss);
}

//Web Page
export const getCarrousel = async(req: Request, res: Response): Promise<Response> =>{
    const imagen = await getRepository(Carrousel).findOne({idImg:req.params.nombre as any});
    if (imagen){
        res.sendFile(path.join(__dirname,'../../Media/'+imagen.img));
    }
    return res;
}

export const getMensaje = async(req: Request, res: Response): Promise<Response> =>{
    console.log(req.body);
    const mensaje = "-- Formulario recibido --" +"\n"+
                    "Nombre: "+ req.body.nombre +"\n"+
                    "Email: "+ req.body.email +"\n"+
                    "Telefono: "+ req.body.telefono +"\n"+
                    "mensaje: "+ req.body.mensaje +"\n"+
                    "\n"+
                    "Favor de contestarle lo mas pronto posible." +"\n"+
                    "Atte. el Bot :)";
    bot.telegram.sendMessage('@EduDownTestForm',mensaje);
    return res.json({});
}

export const Login = async(req: Request, res: Response): Promise<Response> =>{
    console.log(req.body)
    const query = await getRepository(Usuario).findOne({correo : req.body.email, password : req.body.pass});
    if (query) {
        return res.json(query);
    } else {
        return res.status(404).json({msg:'usuario no encontrado'});
    }

}