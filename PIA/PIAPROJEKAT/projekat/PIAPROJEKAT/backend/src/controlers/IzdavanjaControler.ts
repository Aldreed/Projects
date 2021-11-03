import express from 'express'
import Procenat from '../models/Procenat';
import IzdateNekretnine from '../models/IzdateNekretnine';


export default class izdavanjaContoller{

    dodajIzdavanje =(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;
        const vlasnik = req.body.vlasnik;
        const kupac = req.body.kupac;
        const datumOd = req.body.datumOd;
        const datumDo = req.body.datumDo;
        const totalCost = req.body.totalCost;
        const odobreno = req.body.odobreno;


        IzdateNekretnine.find({},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                let IdIzd =1
                if(resp.length>0){
                    IdIzd = resp[0].get('IdIzd')+1;

                }

                Procenat.find({'tip':'izdavanje'},(err,reps2)=>{
                    if(err){
                        console.log(err);
                    }
                    else if(reps2){
                        let procenatObj:any=new Procenat({
                            'IdPro':1,
                            "tip":'izdavanje',
                            "procenat":0.1
                        })
                        if(reps2.length>0){
                            procenatObj = reps2[reps2.length-1];
                        }
                        const procenat=procenatObj["procenat"];
                        let nov = new IzdateNekretnine({
                            IdIzd,
                            IdNek,
                            vlasnik,
                            kupac,
                            datumOd,
                            datumDo,
                            totalCost,
                            odobreno,
                            procenat
                        })
        
                        nov.save().then(nov=>{res.json({'message':'ok'})}).catch(err=>{
                            console.log(err);
                            res.json(err);
                        })
                    }
                })

            }
        }).sort({'IdIzd':-1}).limit(1);
    }

    getIzdavanja=(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;

        IzdateNekretnine.find({'IdNek':IdNek,'odobreno':true},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    getNeodobrenaByIDNek=(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;

        IzdateNekretnine.find({'IdNek':IdNek,'odobreno':false},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    
    getOdobrena=(req:express.Request,res:express.Response)=>{

        IzdateNekretnine.find({'odobreno':true},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    getNeodobrene=(req:express.Request,res:express.Response)=>{

        IzdateNekretnine.find({'odobreno':false},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    odobri=(req:express.Request,res:express.Response)=>{
        const IdIzd = req.body.IdIzd;

        IzdateNekretnine.update({'IdIzd':IdIzd},{$set:{'odobreno':true}},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    odbaci=(req:express.Request,res:express.Response)=>{
        const IdIzd = req.body.IdIzd;

        IzdateNekretnine.remove({'IdIzd':IdIzd,'odobreno':false},(err)=>{
            if(err){
                console.log(err);
            }
            else{
                res.json({'message':'ok'});
            }
    })
    }

}