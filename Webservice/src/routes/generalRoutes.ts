import { Router } from "express";
import { getUsuarios,crearUsuario,getUsuario,updateUsuario,deleteUsuario,findAccount} from "../controllers/generalController";
import { getEmployees } from "../controllers/HRController";

const router = Router();

router.get('/usuarios',getUsuarios);
router.post('/usuario',crearUsuario);
router.post('/usuario/log/',findAccount);
router.get('/usuario/:idUsuario',getUsuario);
router.put('/usuario/:idUsuario',updateUsuario);
router.delete('/usuario/:idUsuario',deleteUsuario);

//HR routes
router.get('/employees',getEmployees);

export default router;