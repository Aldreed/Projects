import express from 'express'
import izdavanjaContoller from '../controlers/IzdavanjaControler';


const izdavanjeRouter = express.Router();

izdavanjeRouter.route('/add').post(
    (req,res)=>new izdavanjaContoller().dodajIzdavanje(req,res)
)

izdavanjeRouter.route('/getIzdavanje').post(
    (req,res)=>new izdavanjaContoller().getIzdavanja(req,res)
)

izdavanjeRouter.route('/getOdobrena').get(
    (req,res)=>new izdavanjaContoller().getOdobrena(req,res)
)

izdavanjeRouter.route('/getNeodobrene').get(
    (req,res)=>new izdavanjaContoller().getNeodobrene(req,res)
)

izdavanjeRouter.route('/odobri').post(
    (req,res)=>new izdavanjaContoller().odobri(req,res)
)

izdavanjeRouter.route('/odbaci').post(
    (req,res)=>new izdavanjaContoller().odbaci(req,res)
)

izdavanjeRouter.route('/getNeodobrenaByIDNek').post(
    (req,res)=>new izdavanjaContoller().getNeodobrenaByIDNek(req,res)
)

export default izdavanjeRouter;