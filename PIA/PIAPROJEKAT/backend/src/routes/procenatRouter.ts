import express from 'express'
import procenatControler from '../controlers/procenatControler';


const procenatRouter = express.Router();


procenatRouter.route('/dodaj').post(
    (req,res)=>new procenatControler().dodaj(req,res)
)


export default procenatRouter;
