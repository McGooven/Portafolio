import { Router } from "express";
import { 
    getUsuarios,
    crearUsuario,
    getUsuario,
    updateUsuario,
    deleteUsuario,
    findAccount,
    getcentros,
    getAtenciones,
    getCarrousel,
    getMensaje,
    Login,
    getBoxesState,
    getPlanification,
    peticionEnfermera,
    getWeekPlanification,
    getTiposSesion,
    getPacandPersAvailables,
    saveAtencion
} from "../controllers/generalController";
import { getEmployees } from "../controllers/HRController";

const router = Router();

router.get('/usuarios',getUsuarios);
router.post('/usuario/C',crearUsuario);
router.get('/centros',getcentros);
router.get('/atenciones',getAtenciones)
router.post('/usuario/log/',findAccount);
router.get('/usuario/:idUsuario',getUsuario);
router.post('/usuario/U',updateUsuario);
router.post('/usuario/D',deleteUsuario);
router.post('/boxes',getBoxesState);
router.post('/filterSesions',getPlanification);
router.post('/peticionEnfermera', peticionEnfermera);
router.post('/weekPlan', getWeekPlanification);
router.post('/TPS',getTiposSesion);
router.post('/pacPerAvailable',getPacandPersAvailables);
router.post('/saveAtencion',saveAtencion);

router.get('/web/carrousel/:nombre',getCarrousel);
router.post('/web/recibirFormulario',getMensaje);
router.post('/web/logear',Login);

//HR routes
router.get('/employees',getEmployees);

export default router;