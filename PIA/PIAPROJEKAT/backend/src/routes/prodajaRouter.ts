import express from 'express'
import prodajaControler from '../controlers/prodajaControler';


const prodajeRouter = express.Router();


prodajeRouter.route('/dodaj').post(
    (req,res)=>new prodajaControler().dodajProdaju(req,res)
)



prodajeRouter.route('/get').post(
    (req,res)=>new prodajaControler().getProdaje(req,res)
)

prodajeRouter.route('/getOdobrene').get(
    (req,res)=>new prodajaControler().getOdobrene(req,res)
)

prodajeRouter.route('/getNeodobrene').get(
    (req,res)=>new prodajaControler().getNeodobrene(req,res)
)

prodajeRouter.route('/odobri').post(
    (req,res)=>new prodajaControler().odobri(req,res)
)

prodajeRouter.route('/odbaci').post(
    (req,res)=>new prodajaControler().odbaciPonude(req,res)
)


export default prodajeRouter;
