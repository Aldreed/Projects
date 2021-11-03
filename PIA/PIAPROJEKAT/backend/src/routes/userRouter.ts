import express, { Router } from 'express'

import userController from '../controlers/userController';

import storage from '../helpers/storage'
import remover from '../helpers/remover'


const userRouter = express.Router();

userRouter.route('/add').post(
    storage,
    (req,res)=>new userController().addUser(req,res)
)

userRouter.route('/prijava').post(
    (req,res)=>new userController().prijava(req,res)
)

userRouter.route('/getAll').get(
    (req,res)=>new userController().getUsers(req,res)
)

userRouter.route('/checkUsername').post(
    (req,res)=>new userController().checkUsername(req,res)
)

userRouter.route('/checkEmail').post(
    (req,res)=>new userController().checkEmail(req,res)
)


userRouter.route('/addT').post(
    (req,res)=>new userController().addUserT(req,res)
)

userRouter.route('/promeniLoz').post(
    (req,res)=>new userController().promeniLoz(req,res)
)

userRouter.route('/odobri').post(
    (req,res)=>new userController().odobri(req,res)
)

userRouter.route('/updateInfo').post(
    (req,res)=>new userController().updateInfo(req,res)
)

userRouter.route('/updatePic').post(
    storage,
    (req,res)=>new userController().updatePic(req,res)
)

userRouter.route('/remove').post(
    (req,res)=>new userController().removeUser(req,res)
)

userRouter.route('/removePic').post(
    (req,res)=>new userController().removePic(req,res)
)



export default userRouter;