const multer = require('multer')
const path = require('path');

const diskStorage = multer.diskStorage({
  destination: (req:any, file:any, cb:any) => {
    cb(null, 'src/MediaImages/');
  },
  filename: (req:any, file:any, cb:any) => {
    const mimeType = file.mimetype.split('/');
    const fileType = mimeType[1];
    const fileName = file.originalname + '.' + fileType;
    cb(null, fileName);
  },
});

const fileFilter = (req:any, file:any, cb:any) => {
  const allowedMimeTypes = ['image/png', 'image/jpeg', 'image/jpg'];
  if('image/png'.match(file.mimetype)){
      cb(null, true);
  }
  if('image/jpeg'.match(file.mimetype)){
    cb(null, true);
    }
    if('image/jpeg'.match(file.mimetype)){
        cb(null, true);
    }
    else{
        cb(null,false);
    }
};

const storageMedia = multer({ storage: diskStorage, fileFilter: fileFilter }).single(
  'image'
);

export default storageMedia;
