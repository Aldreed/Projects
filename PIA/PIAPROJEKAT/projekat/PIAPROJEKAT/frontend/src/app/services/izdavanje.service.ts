import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class IzdavanjeService {

  uri='http://localhost:4000';

  constructor(private http:HttpClient) { }

  dodajIzdavanje(IdNek,vlasnik,kupac,datumOd,datumDo,totalCost,odobreno){
    const data ={
      IdNek:IdNek,
      vlasnik:vlasnik,
      kupac:kupac,
      datumOd:datumOd,
      datumDo:datumDo,
      totalCost:totalCost,
      odobreno:odobreno
    }
 
    return this.http.post(`${this.uri}/izdavanje/add`,data);
    
  }

  getIzdavanja(IdNek){
    const data ={
      IdNek:IdNek
    }

    return this.http.post(`${this.uri}/izdavanje/getIzdavanje`,data);
  }

  getNeodobrenaByIDNek(IdNek){
    const data ={
      IdNek:IdNek
    }

    return this.http.post(`${this.uri}/izdavanje/getNeodobrenaByIDNek`,data);
  }
  
    getNeodobrene(){
      return this.http.get(`${this.uri}/izdavanje/getNeodobrene`)
    }
  
    getOdobrena(){
      return this.http.get(`${this.uri}/izdavanje/getOdobrena`)
    }
  

  odobri(IdIzd){
    const data={
      IdIzd:IdIzd
    }

    return this.http.post(`${this.uri}/izdavanje/odobri`,data);
  }


  odbaci(IdIzd){
    const data={
      IdIzd:IdIzd
    }

    return this.http.post(`${this.uri}/izdavanje/odbaci`,data);
  }


}
