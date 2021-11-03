import express from 'express'
import Procenat from '../models/Procenat';
import prodateNekretnine from '../models/prodateNekretnine';



export default class prodajaControler{

    dodajProdaju =(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;
        const vlasnik = req.body.vlasnik;
        const kupac = req.body.kupac;
        const totalCost = req.body.totalCost;
        const odobreno= req.body.odobreno;


        prodateNekretnine.find({},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                let IdPro=1;
                if(resp.length>0){
                    IdPro = resp[0].get('IdPro')+1;

                }
                Procenat.find({'tip':'prodaja'},(err,resp2)=>{
                    if(err){
                        console.log(err);
                    }
                    else if(resp2){
                        let procenatObj:any=new Procenat({
                            'IdPro':1,
                            "tip":'prodaja',
                            "procenat":0.1
                        })
                        if(resp2.length>0){
                            procenatObj = resp2[resp2.length-1];
                        }
                        const procenat=procenatObj["procenat"];
                        let nov = new prodateNekretnine({
                            IdPro,
                            IdNek,
                            vlasnik,
                            kupac,
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
        }).sort({'IdPro':-1}).limit(1)
    }


    getProdaje=(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;

        prodateNekretnine.find({'IdNek':IdNek,'odobreno':true},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    getOdobrene=(req:express.Request,res:express.Response)=>{

        prodateNekretnine.find({'odobreno':true},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    getNeodobrene=(req:express.Request,res:express.Response)=>{

        prodateNekretnine.find({'odobreno':false},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    odobri=(req:express.Request,res:express.Response)=>{
        const IdPro = req.body.IdPro;

        prodateNekretnine.update({'IdPro':IdPro},{$set:{'odobreno':true}},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    odbaciPonude=(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;
        
        prodateNekretnine.remove({'IdNek':IdNek,'odobreno':false},(err)=>{
            if(err){
                console.log(err);
            }
            else{
                res.json({'message':'ok'});
            }
        })
    }


}