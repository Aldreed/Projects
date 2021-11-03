import mongoose from 'mongoose'

const Schema = mongoose.Schema;

const prodateNekretnine = new Schema({
    IdPro:{
        type:Number
    },
    IdNek:{
        type:Number
    },
    vlasnik:{
        type:String
    },
    kupac:{
        type:String
    },
    totalCost:{
        type:Number
    },
    odobreno:{
        type:Boolean
    },
    procenat:{
        type:Number
    }

})

export default mongoose.model('ProdateNekretnine',prodateNekretnine,'prodateNekretnine');