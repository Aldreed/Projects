import mongoose from 'mongoose'

const Schema = mongoose.Schema;

const izdateNekretnine = new Schema({
    IdIzd:{
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
    datumOd:{
        type:Date
    },
    datumDo:{
        type:Date
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

export default mongoose.model('IzdateNekretnine',izdateNekretnine,'izdateNekretnine');