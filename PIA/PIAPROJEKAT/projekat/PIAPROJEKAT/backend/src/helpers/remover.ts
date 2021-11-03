const fs = require('fs');
import express from 'express'

var remover = function(req:express.Request,res:any,next:any){
    const temp:String =(req.body.oldImage+"") as String; 
    fs.unlink("src"+temp,(err:any)=>{
        if(err){
            console.log(temp);
            console.log(err);
        }
    });
    console.log(req.body.oldImage+"");
    next();
}

export default remover;