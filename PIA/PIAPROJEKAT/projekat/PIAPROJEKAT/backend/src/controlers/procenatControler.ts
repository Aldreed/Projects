import express from 'express'
import Procenat from '../models/Procenat';

export default class procenatControler{

    dodaj=(req:express.Request,res:express.Response)=>{
        const procenat = req.body.procenat;
        const tip = req.body.tip;

        Procenat.find({},(err,resp)=>{
            if(resp){
                let IdProc=1;
                if(resp.length>0){
                     IdProc =resp[0].get('IdProc');
                }
                let nov = new Procenat({
                    IdProc,
                    tip,
                    procenat
                })
                nov.save().then(nov=>res.json({'message':'ok'})).catch(err=>{
                    console.log(err);
                })
            }
        }).sort({'IdProc':-1}).limit(1)
    }


    get=(req:express.Request,res:express.Response)=>{

        Procenat.find({},(err,resp)=>{
           if(err){
               console.log(err);
           }
            else if(resp){
               res.json(resp)
           }

        })
    }
}