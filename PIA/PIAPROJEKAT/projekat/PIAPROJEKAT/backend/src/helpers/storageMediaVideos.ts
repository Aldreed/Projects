const multer = require('multer')
const path = require('path');

const diskStorage = multer.diskStorage({
  destination: (req:any, file:any, cb:any) => {
    cb(null, 'src/MediaVideos/');
  },
  filename: (req:any, file:any, cb:any) => {
    const mimeType = file.mimetype.split('/');
    const fileType = mimeType[1];
    const fileName = file.originalname + '.' + fileType;
    cb(null, fileName);
  },
});

const fileFilter = (req:any, file:any, cb:any) => {
  const allowedMimeTypes = ['video/mp4'];//nije potrebno
    if('video/mp4'.match(file.mimetype)){
        cb(null, true);
    }
    else{
        cb(null,false);
    }
};

const storageMediaVideos = multer({ storage: diskStorage, fileFilter: fileFilter }).single(
  'video'
);

export default storageMediaVideos;
