import e from 'express';
import express from 'express'
import blokiraneVeze from '../models/blokiraneVeze';
import Konverzacija from '../models/Konverzacija';


export default class konverzacijaController{

    getKonverzacije = (req:express.Request,res:express.Response)=>{

        Konverzacija.find({},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }
    
    getKonverzacijaKorisnik = (req:express.Request,res:express.Response)=>{
        const korisnik = req.body.korisnik;

        Konverzacija.find( {$and:[{$or :[{'vlasnik':korisnik},{'kupac':korisnik}]},{'status':'aktivna'}]},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        }).sort({'poslednjaPoruka':-1})
    }
    
    getArhiviranoKorisnik = (req:express.Request,res:express.Response)=>{
        const korisnik = req.body.korisnik;

        Konverzacija.find( {$and:[{$or :[{'vlasnik':korisnik},{'kupac':korisnik}]},{'status':'arhivirana'}]},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        }).sort({'poslednjaPoruka':-1})
    }

    getKonverzacijaVlasnik = (req:express.Request,res:express.Response)=>{
        const vlasnik = req.body.vlasnik;

        Konverzacija.find({'vlasnik':vlasnik},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        }).sort({'poslednjaPoruka':-1})
    }

    getKonverzacijaKupac = (req:express.Request,res:express.Response)=>{
        const kupac = req.body.kupac;

        Konverzacija.find({'kupac':kupac},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    dodajKonverzaciju =(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;
        const naziv = req.body.naziv;
        const vlasnik= req.body.vlasnik;
        const kupac= req.body.kupac;
        const status = req.body.status;
        const poslednjaPoruka = req.body.poslednjaPoruka;
        const poruke = req.body.poruke;
        const vlasnikCur=1;
        const kupacCur=1;
        const Cur=1;
        //PORUKE DODAVANJE
        // //poruka
        // const sender = req.body.sender;
        // const time = req.body.time;
        // const tipPoruke = req.body.tipPoruke;
        // const poruka = req.body.poruka;


        Konverzacija.find({},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                let IdKon =1;
                if(resp.length>0){
                    IdKon = resp[0].get('IdKon')+ 1;

                }
                // const novaPoruka = {
                //     'sender':sender,
                //     'time':time,
                //     'tipPoruke':tipPoruke,
                //     'poruka':poruka
                // }
                
                const nov = new Konverzacija({
                    IdKon,
                    IdNek,
                    naziv,
                    vlasnik,
                    kupac,
                    status,
                    poslednjaPoruka,
                    poruke,
                    vlasnikCur,
                    kupacCur,
                    Cur
                })
                nov.save().then(nov=>res.json({'message':"ok",'IdGen':IdKon})).catch(err=>{
                    console.log(err);
                })
            }
        }).sort({'IdKon':-1}).limit(1)
        
    
    }

    updateCur =(req:express.Request,res:express.Response)=>{
        const cur = req.body.Cur;
        const vlasnikCur=req.body.vlasnikCur;
        const kupacCur = req.body.kupacCur;
        const IdKon = req.body.IdKon;

        Konverzacija.update({'IdKon':IdKon},{$set:{'vlasnikCur':vlasnikCur,'kupacCur':kupacCur,'Cur':cur}},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json({'message':'ok'});
            }
        })
    }

    promeniStatus =(req:express.Request,res:express.Response)=>{
        const status = req.body.status;
        const IdKon = req.body.IdKon;
        
        Konverzacija.update({'IdKon':IdKon},{$set:{'status':status}},(err,resp)=>{
            if(err){
                console.log(err)
            }
            else if(resp){
                res.json({'message':'ok'});
            }    
        })
    }


    dodajPoruku =(req:express.Request,res:express.Response)=>{
        const IdKon = req.body.IdKon;
        const poruka = req.body.poruka;
        
        Konverzacija.update({'IdKon':IdKon},{$set:{'poslednjaPoruka':new Date(poruka.time)},
    $push:{'poruke':poruka}},(err,resp)=>{
        if(err){
            console.log(err)
        }
        else if(resp){
            res.json({'message':'ok'});
        }
    })
    }


    blokiranKorisnik=(req:express.Request,res:express.Response)=>{
        const kor1 = req.body.kor1;
        const kor2 = req.body.kor2;
        
        blokiraneVeze.find({'blokirao':kor1,'blokiran':kor2},(err,resp)=>{
            if(err){
                console.log(err)
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    blokirajKorisnika=(req:express.Request,res:express.Response)=>{
        const blokirao = req.body.blokirao;
        const blokiran = req.body.blokiran;
        blokiraneVeze.find({},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                let IdBlock=1;
                if(resp.length>0){
                     IdBlock = resp[0].get('IdBlock')+1;

                }
                const nov = new blokiraneVeze({
                    IdBlock,
                    blokirao,
                    blokiran
                })
                nov.save().then(nov=>res.json({'message':'ok'})).catch(err=>{
                    console.log(err);
                    res.json(err);
                })

            }
        }).sort({'IdBlock':-1}).limit(1)
    }


    blokiranaVeza =(req:express.Request,res:express.Response)=>{
        const kor1 = req.body.kor1;
        
        blokiraneVeze.find({$or:[{'blokirao':kor1},{'blokiran':kor1}]},(err,resp)=>{
            if(err){
                console.log(err)
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    odblokiraj=(req:express.Request,res:express.Response)=>{
        const kor1 = req.body.kor1;
        const kor2 = req.body.kor2;
        
        blokiraneVeze.findOneAndRemove({'blokirao':kor1,'blokiran':kor2},(err,resp)=>{
            if(err){
                console.log(err)
            }
            else if(resp){
                res.json({'message':'ok'});
            }
        })
    }

    updatePoruke= (req: express.Request,res:express.Response)=>{
        const id = req.body.id;
        const poruke = req.body.poruke;

        Konverzacija.update({'IdKon':id},{$set:{'poruke':poruke}},(err,respon)=>{
            if(err){
                console.log(err);
                res.json(err); //erbaza sd
            }
            else{
                res.json(respon);
            }
        })
    }
}