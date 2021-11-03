import mongoose from 'mongoose'

const Schema = mongoose.Schema;

const media = new Schema({
    IdMedia:{
        type:Number
    },
    nekretninaId:{
        type:Number
    },
    mediaPath:{
        type:String
    },
    mediaType:{
        type:String
    }
})

export default mongoose.model('Media',media,'media');