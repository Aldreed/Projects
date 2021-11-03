import express from 'express';
import cors from 'cors';
import bodyParser from 'body-parser';
import mongoose, { Connection }  from 'mongoose'
import userRouter from './routes/userRouter';
import path from 'path'
import nekretninaRouter from './routes/nekretninaRouter';
import izdavanjeRouter from './routes/izdavanjaRouter';
import prodajeRouter from './routes/prodajaRouter';
import konverzacijaRouter from './routes/konverzacijaRouter';
import procenatRouter from './routes/procenatRouter';

const app = express();


app.use(cors());
app.use(bodyParser.json());

const router = express.Router();
mongoose.connect('mongodb://localhost:27017/projekat');
const con = mongoose.connection;

con.once('open',()=>{
    console.log('mongo ok')
})

app.use('/user',userRouter);
app.use('/nekretnina',nekretninaRouter);
app.use('/izdavanje',izdavanjeRouter);
app.use('/prodaja',prodajeRouter);
app.use('/konverzacija',konverzacijaRouter);
app.use('/procenat',procenatRouter);
app.use('/images', express.static(path.join('src/images')));
app.use('/preparedImages', express.static(path.join('src/preparedImages')));
app.use('/MediaImages', express.static(path.join('src/MediaImages')));
app.use('/MediaVideos', express.static(path.join('src/MediaVideos')));


app.use('/', router);
app.listen(4000, () => console.log(`Express server running on port 4000`));