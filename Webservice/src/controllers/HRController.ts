import {Request,Response} from 'express'
import { getRepository, getConnection } from "typeorm";

import { Employees } from "../entitiesHR/Employees";

export const getEmployees = async (req: Request, res: Response): Promise<Response> => {
    const employees = await getConnection("hrSchema").getRepository(Employees).find();
    console.log(employees);
    return res.json(employees);
}