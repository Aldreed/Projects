import express, { json } from 'express'
import Media from '../models/Media';
import Nekretnina from '../models/Nekretnina';
const fs = require('fs');
export default class nekretninaControler{



    getNekretine = (req: express.Request,res:express.Response)=>{

        Nekretnina.find({},(err,respon)=>{
            if(err){
                console.log(err);
                res.json(err); //erbaza sd
            }
            else{
                res.json(respon);
            }
        })
    }

    getStanoviIzdavanje = (req: express.Request,res:express.Response)=>{

        Nekretnina.find({'odobrena':true,'tipProdaje':"izdavanje",'tip':'stan'},(err,respon)=>{
            if(err){
                console.log(err);
                res.json(err); //erbaza sd
            }
            else{
                res.json(respon);
            }
        })
    }
    getStanoviProdaja = (req: express.Request,res:express.Response)=>{

        Nekretnina.find({'odobrena':true,'tipProdaje':"prodaja",'tip':'stan'},(err,respon)=>{
            if(err){
                console.log(err);
                res.json(err); //erbaza sd
            }
            else{
                res.json(respon);
            }
        })
    }
    getKuceIzdavanje = (req: express.Request,res:express.Response)=>{

        Nekretnina.find({'odobrena':true,'tipProdaje':"izdavanje",'tip':'kuca'},(err,respon)=>{
            if(err){
                console.log(err);
                res.json(err); //erbaza sd
            }
            else{
                res.json(respon);
            }
        })
    }
    getKuceProdaja = (req: express.Request,res:express.Response)=>{

        Nekretnina.find({'odobrena':true,'tipProdaje':"prodaja",'tip':'kuca'},(err,respon)=>{
            if(err){
                console.log(err);
                res.json(err); //erbaza sd
            }
            else{
                res.json(respon);
            }
        })
    }

    getNekretnineById= (req: express.Request,res:express.Response)=>{
        const id = req.body.id;

        Nekretnina.find({'IdNek':id},(err,respon)=>{
            if(err){
                console.log(err);
                res.json(err); //erbaza sd
            }
            else{
                res.json(respon);
            }
        })
    }
    
    getAllMedia =(reg:express.Request,res:express.Response)=>{
        Media.find({},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(res){
                res.json(resp);
            }
        })
    }


    addPicture =(req:any,res:any)=>{
        const nekretninaId = req.body.id;
        const mediaPath = 'http://localhost:4000/MediaImages/' +req.file.filename;
    

        Media.find({},(err,resp)=>{
            if(resp){
                let IdMedia=1;
                if(resp.length>0){
                     IdMedia = resp.length+Date.now();
                }
                const mediaType = "image";    
                const nov = new Media({
                    IdMedia,
                    nekretninaId,
                    mediaPath,
                    mediaType
                })

            nov.save().then(nov=>res.json({'message':'ok'})).catch(err=>{
                console.log(err);
            })
          
            }
        }).sort({'IdMedia':-1}).limit(1);
    }

    addVideo =(req:any,res:any)=>{
        const nekretninaId = req.body.id;
        const mediaPath = 'http://localhost:4000/MediaVideos/' +req.file.filename;
        

        Media.find({},(err,resp)=>{
            if(resp){
                let IdMedia=1;
                if(resp.length>0){
                     IdMedia = resp.length+Date.now();
                }
                const mediaType = "video";  
                const nov = new Media({
                    IdMedia,
                    nekretninaId,
                    mediaPath,
                    mediaType
                })

            nov.save().then(nov=>res.json({'message':'ok'})).catch(err=>{
                console.log(err);
            })
          
            }
        }).sort({'IdMedia':-1}).limit(1);
    }

    updateMedia = (req:express.Request,res:express.Response)=>{
        //todo
    }


    getFeatured =(req:express.Request,res:express.Response)=>{
        Nekretnina.find({'featured':true,'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    getNotFeatured =(req:express.Request,res:express.Response)=>{
        Nekretnina.find({'featured':false,'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    getMedia =(req:express.Request,res:express.Response)=>{
        const nekretninaId = req.body.id;
        
        Media.find({'nekretninaId': nekretninaId},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    addNekretnina =(req :express.Request,res:express.Response)=>{
        const naziv = req.body.naziv;
        const grad = req.body.grad;
        const opstina = req.body.opstina;
        const ulica = req.body.ulica;
        const brojUlice = req.body.brojUlice;
        const tip = req.body.tip;
        const brSpratova=req.body.brSpratova;
        const naKomSpratu=req.body.naKomSpratu;
        const spratoviZgrade=req.body.spratoviZgrade;
        const kvadratura = req.body.kvadratura;
        const brSoba = req.body.brSoba;
        const namestena = req.body.namestena;
        const tipProdaje = req.body.tipProdaje;
        const cena = req.body.cena;
        const vlasnik = req.body.vlasnik;
        const odobrena = req.body.odobrena;
        const featured = false;
        const visits = 0;

        Nekretnina.find({},(err,respon)=>{
            if(err){
                console.log(err);
            }
            else{
                let IdNek=1;
                if(respon.length>0){
                    IdNek=respon[0].get('IdNek') + 1;

                }
                const nov= new Nekretnina({
                    IdNek,
                    naziv,
                    grad,
                    opstina,
                    ulica,
                    brojUlice,
                    tip,
                    brSpratova,
                    naKomSpratu,
                    spratoviZgrade,
                    kvadratura,
                    brSoba,
                    namestena,
                    tipProdaje,
                    cena,
                    vlasnik,
                    odobrena,
                    featured,
                    visits
                })

                nov.save().then(nov=>res.json({'message':'ok','generatedID':IdNek})).catch(err=>{
                    console.log(err);
                })


            }
        }).sort({'IdNek':-1}).limit(1);
    }

    getNekretnineForVlasnik = (req: express.Request,res:express.Response)=>{
        const vlasnik = req.body.vlasnik;

        Nekretnina.find({'vlasnik':vlasnik},(err,respon)=>{
            if(err){
                console.log(err);
                res.json(err); //erbaza sd
            }
            else{
                res.json(respon);
            }
        })
    }


    updateTextNekretnine = (req: express.Request,res:express.Response)=>{
        const id = req.body.id;
        const naziv = req.body.naziv;
        const grad = req.body.grad;
        const opstina = req.body.opstina;
        const ulica = req.body.ulica;
        const brojUlice = req.body.brojUlice;
        const brSpratova=req.body.brSpratova;
        const naKomSpratu=req.body.naKomSpratu;
        const spratoviZgrade=req.body.spratoviZgrade;
        const kvadratura = req.body.kvadratura;
        const brSoba = req.body.brSoba;
        const namestena = req.body.namestena;
        const tipProdaje = req.body.tipProdaje;
        const cena = req.body.cena;


        Nekretnina.update({'IdNek':id},{$set:{
            'naziv':naziv,
            'grad':grad,
            'opstina':opstina,
            'ulica':ulica,
            'brojUlice':brojUlice,
            'brSpratova':brSpratova,
            'naKomSpratu':naKomSpratu,
            'spratoviZgrade':spratoviZgrade,
            'kvadratura':kvadratura,
            'brSoba':brSoba,
            'namestena':namestena,
            'tipProdaje':tipProdaje,
            'cena':cena
        }},(err,rest)=>{
            if(err){
                console.log(err);
            }
            else{
                console.log(res);
                res.json({'message':'ok'});
            }
        })

    }

    updateVisitsNekretnine = (req: express.Request,res:express.Response)=>{
        const id = req.body.id;
        const visits = req.body.visits;

        Nekretnina.update({'IdNek':id},{$set:{
            'visits':visits
        }},(err,rest)=>{
            if(err){
                console.log(err);
            }
            else{
                
                res.json({'message':'ok'});
            }
        })

    }

    pretragaPoGradu=(req:express.Request,res:express.Response)=>{
        const grad = req.body.grad;
        
        Nekretnina.find({'grad':grad,'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }


    pretragaPoCeni = (req:express.Request,res:express.Response)=>{
        const minPrice = req.body.minPrice;
        const maxPrice = req.body.maxPrice;

        Nekretnina.find({'cena':{
            $gte:minPrice,
            $lte:maxPrice
        },'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
                //res.json(err); da ne bude error kkada se ugasi baza
            }
            else{
                res.json(resp);
            }
        })
    }

    pretragaPoCeniIGradu =(req:express.Request,res:express.Response)=>{
        const minPrice = req.body.minPrice;
        const maxPrice = req.body.maxPrice;
        const grad = req.body.grad;

        Nekretnina.find({'cena':{
            $gte:minPrice,
            $lte:maxPrice
        },'grad':grad,'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
                //res.json(err); da ne bude error kkada se ugasi baza
            }
            else{
                res.json(resp);
            }
        })
    }

    pretragaIspodCene = (req:express.Request,res:express.Response)=>{
        const maxPrice = req.body.maxPrice;
        

        Nekretnina.find({'cena':{
            $lte:maxPrice
        },'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
                //res.json(err); da ne bude error kkada se ugasi baza
            }
            else{
                res.json(resp);
            }
        })
    }
    pretragaIznadCene = (req:express.Request,res:express.Response)=>{
        const minPrice = req.body.minPrice;
        

        Nekretnina.find({'cena':{
            $gte:minPrice
        },'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
                //res.json(err); da ne bude error kkada se ugasi baza
            }
            else{
                res.json(resp);
            }
        })
    }

    pretragaIspodCeneIGradu = (req:express.Request,res:express.Response)=>{
        const maxPrice = req.body.maxPrice;
        const grad = req.body.grad;

        Nekretnina.find({'cena':{
            $lte:maxPrice
        },'grad':grad,'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
                //res.json(err); da ne bude error kkada se ugasi baza
            }
            else{
                res.json(resp);
            }
        })
    }

    pretragaIznadCeneIGradu = (req:express.Request,res:express.Response)=>{
        const minPrice = req.body.minPrice;
        const grad = req.body.grad;

        Nekretnina.find({'cena':{
            $gte:minPrice
        },'grad':grad,'odobrena':true},(err,resp)=>{
            if(err){
                console.log(err);
                //res.json(err); da ne bude error kkada se ugasi baza
            }
            else{
                res.json(resp);
            }
        })
    }

    getPictures =(req:express.Request,res:express.Response)=>{
        const nekretninaId = req.body.id
        const mediaType =req.body.mediaType;

        Media.find({'nekretninaId': nekretninaId,'mediaType':mediaType},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    getNeodobrene =(req:express.Request,res:express.Response)=>{
        Nekretnina.find({'odobrena':false},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    odobri=(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;

        Nekretnina.update({'IdNek':IdNek},{$set:{'odobrena':true}},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    feature=(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;

        Nekretnina.update({'IdNek':IdNek},{$set:{'featured':true}},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    featureStop=(req:express.Request,res:express.Response)=>{
        const IdNek = req.body.IdNek;

        Nekretnina.update({'IdNek':IdNek},{$set:{'featured':false}},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
    })
    }

    removePic=(req:express.Request,res:express.Response)=>{
    const oldImage=req.body.oldImage;
       console.log(oldImage);

       fs.unlink("src/"+oldImage,(err:any)=>{
        if(err){
            console.log(oldImage);
            console.log(err);
            res.json({'message':'err'});
        }
        else{
            res.json({'message':'removedPic'});
        }
    });
    }

    removeVid=(req:express.Request,res:express.Response)=>{
        const oldImage=req.body.oldImage;
           console.log(oldImage);
    
           fs.unlink("src/"+oldImage,(err:any)=>{
            if(err){
                console.log(oldImage);
                console.log(err);
                res.json({'message':'err'});
            }
            else{
                res.json({'message':'removedVid'});
            }
        });
        }

        removeMedia=(req:express.Request,res:express.Response)=>{
            const IdMedia = req.body.IdMedia;
            
            Media.remove({'IdMedia':IdMedia},(err)=>{
                if(err){
                    console.log(err);
                }
                else{
                    res.json({'message':'removed'});
                }
            })
            }
}