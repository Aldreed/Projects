import mongoose from 'mongoose'

const Schema = mongoose.Schema;

const block = new Schema({
    IdBlock:{
        type:Number
    },
    blokirao:{
        type:String
    },
    blokiran:{
        type:String
    }
})

export default mongoose.model('BlokiraniKorisnici',block,'blokiraniKorisnici');