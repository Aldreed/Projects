import express, { json } from 'express'
import User from '../models/User';
const fs = require('fs');

export default class userController{

    getUsers = (req : express.Request,res:express.Response)=>{
        const profiles = User.find({},(err,resp)=>{
            res.json(resp);
        });
    }

    addUser = (req:any,res:any)=>{
        
        const ime = req.body.ime;
        const prezime = req.body.prezime;
        const username = req.body.username;
        const lozinka = req.body.lozinka;
        const email = req.body.email;
        const grad = req.body.grad;
        const drzava = req.body.drzava;
        const tip = req.body.tip;
        const odobren = req.body.odobren;
         const imagePath = 'http://localhost:4000/images/' + req.file.filename; 
    


        let gateUsername:boolean=true;
        let gateEmail:boolean=true;
        
        User.findOne({'username':username},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                gateUsername=false;
                console.log('dd');
                res.json({'usernameError':'username Error'});
            }
            else{
                gateUsername=true;
                console.log('dd1');
                User.findOne({'email':email},(err,resp)=>{
                    if(err){
                        console.log(err);
                    }
                    else if(resp){
                        //res.json({'message':'not ok'});
                        gateEmail = false;
                        console.log('ddd');
                        res.json({'emailError':'email Error'});
                    }
                    else{
                        gateEmail=true;
                        console.log('ddd2');
                        if(gateEmail&&gateUsername){
                            //console.log(gateEmail);
                            //console.log(gateUsername);
                
                            const nov = new User({
                                ime,
                                prezime,
                                username,
                                lozinka,
                                email,
                                grad,
                                drzava,
                                imagePath,
                                tip,
                                odobren
                            })
                    
                            console.log(nov);
                            nov.save().then(nov=>res.json({'message':'ok'})).catch(err=>{
                                console.log(err);
                            })
                        }
                    }
                });
            }
        });

    }

    addUserT = (req:express.Request,res:express.Response)=>{
        
        const ime = req.body.ime;
        const prezime = req.body.prezime;
        const username = req.body.username;
        const lozinka = req.body.lozinka;
        const email = req.body.email;
        const grad = req.body.grad;
        const drzava = req.body.drzava;
        const tip = req.body.tip;
        const odobren = req.body.odobren;
        const imagePath = 'http://localhost:4000/preparedImages/basic.png';

        let gateUsername:boolean=true;
        let gateEmail:boolean=true;
        
        User.findOne({'username':username},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                gateUsername=false;
                console.log('dd');
                res.json({'usernameError':'username Error'});
            }
            else{
                gateUsername=true;
                console.log('dd1');
                User.findOne({'email':email},(err,resp)=>{
                    if(err){
                        console.log(err);
                    }
                    else if(resp){
                        //res.json({'message':'not ok'});
                        gateEmail = false;
                        console.log('ddd');
                        res.json({'emailError':'email Error'});
                    }
                    else{
                        gateEmail=true;
                        console.log('ddd2');
                        if(gateEmail&&gateUsername){
                            //console.log(gateEmail);
                            //console.log(gateUsername);
                
                            const nov = new User({
                                ime,
                                prezime,
                                username,
                                lozinka,
                                email,
                                grad,
                                drzava,
                                imagePath,
                                tip,
                                odobren
                            })
                    
                            console.log(nov);
                            nov.save().then(nov=>res.json({'message':'ok'})).catch(err=>{
                                console.log(err);
                            })
                        }
                    }
                });
            }
        });

    }


    checkUsername = (req : express.Request,res:express.Response)=>{
        const username = req.body.username;
        User.findOne({'username':username},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                //res.json({'message':'not ok'});
                res.json(resp);

            }
            else{
                res.json(null);
            }

        });
    }

    checkEmail = (req : express.Request,res:express.Response)=>{
        const email = req.body.email;
        User.findOne({'email':email},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                //res.json({'message':'not ok'});
                res.json(resp);
            }else{
                res.json(null);
            }
        });
    }


   prijava =(req:express.Request,res:express.Response)=>{
       const username = req.body.username;
       const lozinka = req.body.lozinka;
       console.log(username,lozinka);


       User.find({'username':username,'lozinka':lozinka},(err,resp)=>{
        if(err){
            console.log(err);
        }
        else if(resp){
            
            res.json(resp);
        }
       })
   }


   promeniLoz=(req:express.Request,res:express.Response)=>{
       const username = req.body.username;
       const lozinka = req.body.lozinka;

       
       User.update({'username':username},{$set:{'lozinka':lozinka}},(err,resp)=>{
           if(err){
               console.log(err);
           }
           else if (resp){
               console.log(resp);
               res.json({'message':'ok'});
           }
       })
   }


   odobri = (req : express.Request,res:express.Response)=>{
        const username =req.body.username;
        User.update({'username':username},{$set:{'odobren':true}},(err,resp)=>{
            if(err){
                console.log(err);
            }
            else if(resp){
                res.json(resp);
            }
        })
    }

    updateInfo=(req:any,res:any)=>{//email + slika ?
       const username = req.body.username;
       const newUsername=req.body.newUsername;
       const ime = req.body.ime;
       const prezime = req.body.prezime;
       const grad = req.body.grad;
       const drzava = req.body.drzava;
       const tip = req.body.tip;
       const email = req.body.email;

       console.log(username);
       console.log(ime);

       User.update({'username':username},{$set:{'ime':ime,'prezime':prezime,'grad':grad,'drzava':drzava,'tip':tip,'username':newUsername,'email':email}},(err,resp)=>{
           if(err){
               console.log(err);
           }
           else if (resp){
               console.log(resp);
               res.json({'message':'ok'});
           }
       })
   }

   updatePic =(req:any,res:any)=>{
    const username = req.body.username;
    const imagePath = 'http://localhost:4000/images/' + req.file.filename;

    User.update({'username':username},{$set:{'imagePath':imagePath}},(err,resp)=>{
        if(err){
            console.log(err);
        }
        else if (resp){
            console.log(resp);
            res.json({'message':'ok'});
        }
    })
   }

   removeUser=(req:express.Request,res:express.Response)=>{
    const username = req.body.username;
    
    User.remove({'username':username},(err)=>{
        if(err){
            console.log(err);
        }
        else{
            res.json({'message':'removed'});
        }
    })
    }

    removePic=(req:express.Request,res:express.Response)=>{
        const oldImage=req.body.oldImage;
       console.log(oldImage);

       fs.unlink("src/images"+oldImage,(err:any)=>{
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

}