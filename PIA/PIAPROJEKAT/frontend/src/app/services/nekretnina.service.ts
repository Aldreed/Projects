import { HttpClient } from '@angular/common/http';
import { identifierModuleUrl } from '@angular/compiler';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NekretninaService {

  uri='http://localhost:4000'


  constructor(private http:HttpClient) { }

  dodajNekretninu(naziv,grad,opstina,ulica,brojUlice,tip,brSpratova,naKomSpratu,spratoviZgrade,kvadratura,brSoba,namestena,tipProdaje,cena,vlasnik,odobrena){
    const data ={
      naziv:naziv,
      grad:grad,
      opstina:opstina,
      ulica:ulica,
      brojUlice:brojUlice,
      tip:tip,
      brSpratova:brSpratova,
      naKomSpratu:naKomSpratu,
      spratoviZgrade:spratoviZgrade,
      kvadratura:kvadratura,
      brSoba:brSoba,
      namestena:namestena,
      tipProdaje:tipProdaje,
      cena:cena,
      vlasnik:vlasnik,
      odobrena:odobrena
    }

    return this.http.post(`${this.uri}/nekretnina/dodajNekretninu`,data);

  }

  dodajSliku(id,image,username){
    const data = new FormData();
    data.append('id',id);
    data.append('image',image,username+image.name+Date.now().toString());//ime fajla

    return this.http.post(`${this.uri}/nekretnina/dodajSliku`,data);
  }


  dodajVideo(id,video,username){
    const data = new FormData();
    data.append('id',id);
    data.append('video',video,username+video.name+Date.now().toString());//ime fajla

    return this.http.post(`${this.uri}/nekretnina/dodajVideo`,data);
  }

  getNekretininaByVlasnik(vlasnik){
    const data ={
      vlasnik:vlasnik
    }

    return this.http.post(`${this.uri}/nekretnina/getByVlasnik`,data);
  }

  getAllMedia(){
    return this.http.get(`${this.uri}/nekretnina/getAll`);
  }

  getFeaturedNekretina(){
    return this.http.get(`${this.uri}/nekretnina/getFeatured`);
  }

  getNotFeatured(){
    return this.http.get(`${this.uri}/nekretnina/getNotFeatured`);
  }

  pretragaPoGradu(grad){
    const data ={
      grad:grad
    }
    return this.http.post(`${this.uri}/nekretnina/pretragaPoGradu`,data)
  }

  
  pretragaPoCeni(minPrice,maxPrice){
    const data ={
      minPrice:minPrice,
      maxPrice:maxPrice
    }
    return this.http.post(`${this.uri}/nekretnina/pretragaPoCeni`,data)
  }

  pretragaPoCeniIGradu(minPrice,maxPrice,grad){
    const data ={
      minPrice:minPrice,
      maxPrice:maxPrice,
      grad:grad
    }
    return this.http.post(`${this.uri}/nekretnina/pretragaPoCeniIGradu`,data)
  }

  pretragaIznadCene(minPrice){
    const data ={
      minPrice:minPrice,
    }
    return this.http.post(`${this.uri}/nekretnina/pretragaIznadCene`,data)
  }

  pretragaIspodCene(maxPrice){
    const data ={
      maxPrice:maxPrice
    }
    return this.http.post(`${this.uri}/nekretnina/pretragaIspodCene`,data)
  }

  pretragaIznadCeneIGradu(minPrice,grad){
    const data ={
      minPrice:minPrice,
      grad:grad
    }
    return this.http.post(`${this.uri}/nekretnina/pretragaIznadCeneIGradu`,data)
  }
  
  
  pretragaIspodCeneIGradu(maxPrice,grad){
    const data ={
      maxPrice:maxPrice,
      grad:grad
    }
    return this.http.post(`${this.uri}/nekretnina/pretragaIspodCeneIGradu`,data)
  }


  getMedia(id){
    const data ={
      id:id
    }

    return this.http.post(`${this.uri}/nekretnina/getMedia`,data)

  }

  getPictures(id){
    const data={
      id:id,
      mediaType:'image'
    }

    return this.http.post(`${this.uri}/nekretnina/getPictures`,data)
  }

  getNeodobrene(){
    return this.http.get(`${this.uri}/nekretnina/getNeodobrene`);
  }


  odobri(IdNek){
    const data={
      IdNek:IdNek
    }

    return this.http.post(`${this.uri}/nekretnina/odobri`,data)
  }

  feature(IdNek){
    const data={
      IdNek:IdNek
    }

    return this.http.post(`${this.uri}/nekretnina/feature`,data)
  }

  featureStop(IdNek){
    const data={
      IdNek:IdNek
    }

    return this.http.post(`${this.uri}/nekretnina/featureStop`,data)
  }
  
  
  updateNekretninu(id,naziv,grad,opstina,ulica,brojUlice,brSpratova,naKomSpratu,spratoviZgrade,kvadratura,brSoba,namestena,tipProdaje,cena){
    const data ={
      id:id,
      naziv:naziv,
      grad:grad,
      opstina:opstina,
      ulica:ulica,
      brojUlice:brojUlice,
      brSpratova:brSpratova,
      naKomSpratu:naKomSpratu,
      spratoviZgrade:spratoviZgrade,
      kvadratura:kvadratura,
      brSoba:brSoba,
      namestena:namestena,
      tipProdaje:tipProdaje,
      cena:cena,
    }
    
    return this.http.post(`${this.uri}/nekretnina/updateNek`,data);
    
  }
  
  getById(id){
    const data={
      id:id
    }
  
    return this.http.post(`${this.uri}/nekretnina//getById`,data)
  }

  getStanIzd(){
    return this.http.get(`${this.uri}/nekretnina/getStanIz`)
  }
  getStanProd(){
    return this.http.get(`${this.uri}/nekretnina/getStanProd`)
  }
  getKucaIzd(){
    return this.http.get(`${this.uri}/nekretnina/getKucaIz`)
  }
  getKucaProd(){
    return this.http.get(`${this.uri}/nekretnina/getKucaProd`)
  }
  getAllNekretina(){
    return this.http.get(`${this.uri}/nekretnina/getAllNekretnina`)
  }
  updateVisits(id,visits){
    const data={
      id:id,
      visits:visits
    }
    return this.http.post(`${this.uri}/nekretnina/updateVisits`,data);
  }

  removePic(oldImage){
    const data ={
      oldImage:oldImage
    }
    return this.http.post(`${this.uri}/nekretnina/removePic`,data)
  }

  removeVid(oldImage){
    const data ={
      oldImage:oldImage
    }
    return this.http.post(`${this.uri}/nekretnina/removeVid`,data)
  }

  remove(IdMedia){
    const data ={
      IdMedia:IdMedia
    }
    return this.http.post(`${this.uri}/nekretnina/remove`,data)
  }
}
