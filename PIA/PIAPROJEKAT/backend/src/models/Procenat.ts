import mongoose from 'mongoose'

const Schema = mongoose.Schema;

const procenat = new Schema({
    IdProc:{
        type:Number
    },
    tip:{
        type:String
    },
    procenat:{
        type:Number
    }
})

export default mongoose.model('Procenat',procenat,'procenat');