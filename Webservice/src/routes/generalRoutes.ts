import { Router } from "express";
import { getUsuarios,crearUsuario,getUsuario,updateUsuario,deleteUsuario,findAccount,getcentros, getAtenciones} from "../controllers/generalController";
import { getEmployees } from "../controllers/HRController";

const router = Router();

router.get('/usuarios',getUsuarios);
router.post('/usuario/C',crearUsuario);
router.get('/centros',getcentros);
router.get('/atenciones',getAtenciones)
router.post('/usuario/log/',findAccount);
router.get('/usuario/:idUsuario',getUsuario);
router.post('/usuario/U',updateUsuario);
router.delete('/usuario/:idUsuario',deleteUsuario);

//HR routes
router.get('/employees',getEmployees);

export default router;