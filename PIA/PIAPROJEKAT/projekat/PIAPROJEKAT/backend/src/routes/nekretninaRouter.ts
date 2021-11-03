import express, { Router } from 'express'
import storageMedia from '../helpers/storageMedia';
import nekretninaControler from '../controlers/nekretninaControler';


import storage from '../helpers/storage'
import storageMediaVideos from '../helpers/storageMediaVideos';


const nekretninaRouter = express.Router();


nekretninaRouter.route('/getAllNekretnina').get(
    (req,res)=>new nekretninaControler().getNekretine(req,res)
)


nekretninaRouter.route('/dodajNekretninu').post(
    (req,res)=>new nekretninaControler().addNekretnina(req,res)
)


nekretninaRouter.route('/dodajSliku').post(
    storageMedia,
    (req,res)=>new nekretninaControler().addPicture(req,res)
)

nekretninaRouter.route('/getByVlasnik').post(
    (req,res)=>new nekretninaControler().getNekretnineForVlasnik(req,res)
)


nekretninaRouter.route('/dodajVideo').post(
    storageMediaVideos,
    (req,res)=>new nekretninaControler().addVideo(req,res)
)

nekretninaRouter.route('/getAll').get(
    (req,res)=>new nekretninaControler().getAllMedia(req,res)
)

nekretninaRouter.route('/getFeatured').get(
    (req,res)=>new nekretninaControler().getFeatured(req,res)
)
nekretninaRouter.route('/getNotFeatured').get(
    (req,res)=>new nekretninaControler().getNotFeatured(req,res)
)

nekretninaRouter.route('/pretragaPoGradu').post(
    (req,res)=> new nekretninaControler().pretragaPoGradu(req,res)
)


nekretninaRouter.route('/pretragaPoCeni').post(
    (req,res)=> new nekretninaControler().pretragaPoCeni(req,res)
)

nekretninaRouter.route('/pretragaPoCeniIGradu').post(
    (req,res)=> new nekretninaControler().pretragaPoCeniIGradu(req,res)
)

nekretninaRouter.route('/pretragaIspodCene').post(
    (req,res)=> new nekretninaControler().pretragaIspodCene(req,res)
)


nekretninaRouter.route('/pretragaIznadCene').post(
    (req,res)=> new nekretninaControler().pretragaIznadCene(req,res)
)


nekretninaRouter.route('/pretragaIspodCeneIGradu').post(
    (req,res)=> new nekretninaControler().pretragaIspodCeneIGradu(req,res)
)


nekretninaRouter.route('/pretragaIznadCeneIGradu').post(
    (req,res)=> new nekretninaControler().pretragaIznadCeneIGradu(req,res)
)

nekretninaRouter.route('/getMedia').post(
    (req,res)=> new nekretninaControler().getMedia(req,res)
)

nekretninaRouter.route('/getPictures').post(
    (req,res)=> new nekretninaControler().getPictures(req,res)
)

nekretninaRouter.route('/getNeodobrene').get(
    (req,res)=> new nekretninaControler().getNeodobrene(req,res)
)

nekretninaRouter.route('/odobri').post(
    (req,res)=> new nekretninaControler().odobri(req,res)
)

nekretninaRouter.route('/feature').post(
    (req,res)=> new nekretninaControler().feature(req,res)
)

nekretninaRouter.route('/featureStop').post(
    (req,res)=> new nekretninaControler().featureStop(req,res)
)

nekretninaRouter.route('/updateNek').post(
    (req,res)=> new nekretninaControler().updateTextNekretnine(req,res)
)

nekretninaRouter.route('/updateVisits').post(
    (req,res)=> new nekretninaControler().updateVisitsNekretnine(req,res)
)
nekretninaRouter.route('/getById').post(
    (req,res)=> new nekretninaControler().getNekretnineById(req,res)
)



nekretninaRouter.route('/getStanIz').get(
    (req,res)=> new nekretninaControler().getStanoviIzdavanje(req,res)
)

nekretninaRouter.route('/getStanProd').get(
    (req,res)=> new nekretninaControler().getStanoviProdaja(req,res)
)

nekretninaRouter.route('/getKucaIz').get(
    (req,res)=> new nekretninaControler().getKuceIzdavanje(req,res)
)

nekretninaRouter.route('/getKucaProd').get(
    (req,res)=> new nekretninaControler().getKuceProdaja(req,res)
)


nekretninaRouter.route('/removePic').post(
    (req,res)=> new nekretninaControler().removePic(req,res)
)
nekretninaRouter.route('/removeVid').post(
    (req,res)=> new nekretninaControler().removeVid(req,res)
)

nekretninaRouter.route('/remove').post(
    (req,res)=> new nekretninaControler().removeMedia(req,res)
)


export default nekretninaRouter;