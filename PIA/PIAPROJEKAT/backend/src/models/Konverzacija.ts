import mongoose from 'mongoose'

const Schema = mongoose.Schema;

const konverzacija = new Schema({
    IdKon:{
        type:Number
    },
    IdNek:{
        type:Number
    },
    naziv:{
        type:String
    },
    vlasnik:{
        type:String
    },
    kupac:{
        type:String
    },
    status:{
        type:String
    },
    poslednjaPoruka:{
        type:Date
    },
    poruke:{
        type:Array
    },
    vlasnikCur:{
        type:Number
    },
    kupacCur:{
        type:Number
    },
    Cur:{
        type:Number
    }


})

export default mongoose.model('Konverzacija',konverzacija,'konverzacije');