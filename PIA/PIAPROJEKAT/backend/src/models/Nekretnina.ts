import mongoose from 'mongoose'

const Schema = mongoose.Schema;

const Nekretnina = new Schema({
    IdNek:{
        type:Number
    },
    naziv:{
        type:String
    },
    grad:{
        type:String
    },
    opstina:{
        type:String
    },
    ulica:{
        type:String
    },
    brojUlice:{
        type:Number
    },
    tip:{
        type:String
    },
    brSpratova:{
        type:Number
    },
    naKomSpratu:{
        type:Number
    },
    spratoviZgrade:{
        type:Number
    },
    kvadratura:{
        type:Number
    },
    brSoba:{
        type:Number
    },
    namestena:{
        type:Boolean
    },
    tipProdaje:{
        type:String
    },
    cena:{
        type:Number
    },
    vlasnik:{
        type:String
    },
    odobrena:{
        type:Boolean
    },
    featured:{
        type:Boolean
    },
    visits:{
        type:Number
    }
    
})

export default mongoose.model('Nekretnina',Nekretnina,'nekretnine');