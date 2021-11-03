import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProdajaService {

  uri = 'http://localhost:4000'

  constructor(private http:HttpClient) { }

  dodajProdaja(IdNek,vlasnik,kupac,totalCost,odobeno){
    const data ={
      IdNek:IdNek,
      vlasnik:vlasnik,
      kupac:kupac,
      totalCost:totalCost,
      odobreno:odobeno
    }
 
    return this.http.post(`${this.uri}/prodaja/dodaj`,data);
    
  }

  getProdaja(IdNek){
    const data ={
      IdNek:IdNek
    }

    return this.http.post(`${this.uri}/prodaja/get`,data);
  }


  getOdobrene(){
    return this.http.get(`${this.uri}/prodaja/getOdobrene`)
  }

  getNeodobrene(){
    return this.http.get(`${this.uri}/prodaja/getNeodobrene`)
  }

  odobri(IdPro){
    const data={
      IdPro:IdPro
    }

    return this.http.post(`${this.uri}/prodaja/odobri`,data);
  }


  odbaci(IdNek){
    const data={
      IdNek:IdNek
    }

    return this.http.post(`${this.uri}/prodaja/odbaci`,data);
  }

}
