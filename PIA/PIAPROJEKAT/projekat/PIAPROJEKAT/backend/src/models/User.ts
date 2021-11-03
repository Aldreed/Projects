import mongoose from 'mongoose'

const Schema = mongoose.Schema;

const user = new Schema({
    ime:{
        type:String
    },
    prezime:{
        type:String
    },
    username:{
        type:String
    },
    lozinka:{
        type:String
    },
    email:{
        type:String
    },
    grad:{
        type:String
    },
    drzava:{
        type:String
    },
    imagePath:{
        type:String
    },
    tip:{
        type:String
    },
    odobren:{
        type:Boolean
    }
})

export default mongoose.model('User',user,'users');