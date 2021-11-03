import express, { Router } from 'express'
import konverzacijaController from '../controlers/konverzacijaController';
import prodajaControler from '../controlers/prodajaControler';


const konverzacijaRouter = express.Router();


konverzacijaRouter.route('/getKonverzacije').get(
    (req,res)=>new konverzacijaController().getKonverzacije(req,res)
)



konverzacijaRouter.route('/getKonverzacijaVlasnik').post(
    (req,res)=>new konverzacijaController().getKonverzacijaVlasnik(req,res)
)


konverzacijaRouter.route('/getKonverzacijaKupac').post(
    (req,res)=>new konverzacijaController().getKonverzacijaKupac(req,res)
)

konverzacijaRouter.route('/dodajKonverzaciju').post(
    (req,res)=>new konverzacijaController().dodajKonverzaciju(req,res)
)

konverzacijaRouter.route('/promeniStatus').post(
    (req,res)=>new konverzacijaController().promeniStatus(req,res)
)

konverzacijaRouter.route('/dodajPoruku').post(
    (req,res)=>new konverzacijaController().dodajPoruku(req,res)
)

konverzacijaRouter.route('/updateCur').post(
    (req,res)=>new konverzacijaController().updateCur(req,res)
)

konverzacijaRouter.route('/blokiranKorisnik').post(
    (req,res)=>new konverzacijaController().blokiranKorisnik(req,res)
)

konverzacijaRouter.route('/blokirajKorisnika').post(
    (req,res)=>new konverzacijaController().blokirajKorisnika(req,res)
)
konverzacijaRouter.route('/blokiranaVeza').post(
    (req,res)=>new konverzacijaController().blokiranaVeza(req,res)
)
konverzacijaRouter.route('/odblokiraj').post(
    (req,res)=>new konverzacijaController().odblokiraj(req,res)
)

konverzacijaRouter.route('/getKonverzacijaKorisnik').post(
    (req,res)=>new konverzacijaController().getKonverzacijaKorisnik(req,res)
)

konverzacijaRouter.route('/getArhiviranoKorisnik').post(
    (req,res)=>new konverzacijaController().getArhiviranoKorisnik(req,res)
)
konverzacijaRouter.route('/updatePoruke').post(
    (req,res)=>new konverzacijaController().updatePoruke(req,res)
)

export default konverzacijaRouter;
