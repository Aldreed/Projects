import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class KonverzacijaService {


  uri='http://localhost:4000';

  constructor(private http:HttpClient) { }
  
  getKonverzacije(){
    return this.http.get(`${this.uri}/konverzacija/getKonverzacije`);
  }
  
  
  getKonverzacijaKorisnik(korisnik){
    
    const data ={
      korisnik:korisnik
    }
    
    return this.http.post(`${this.uri}/konverzacija/getKonverzacijaKorisnik`,data);
  }
  
  getArhiviranoKorisnik(korisnik){
  
  const data ={
    korisnik:korisnik
  }

  return this.http.post(`${this.uri}/konverzacija/getArhiviranoKorisnik`,data);
}
  getKonverzacijaVlasnik(vlasnik){
    
    const data ={
      vlasnik:vlasnik
    }

    return this.http.post(`${this.uri}/konverzacija/getKonverzacijaVlasnik`,data);
  }

  getKonverzacijaKupac(kupac){
    
    const data ={
      kupac:kupac
    }
    
    return this.http.post(`${this.uri}/konverzacija/getKonverzacijaKupac`,data);
  }
  

dodajKonverzaciju(IdNek,naziv,vlasnik,kupac,status,poslednjaPoruka,poruke){
  const data ={
    IdNek:IdNek,
    naziv:naziv,
    vlasnik:vlasnik,
    kupac:kupac,
    status:status,
    poslednjaPoruka:poslednjaPoruka,
    poruke:poruke
  }
  return this.http.post(`${this.uri}/konverzacija/dodajKonverzaciju`,data)

}

promeniStatus(status,IdKon){
  const data ={
    status:status,
    IdKon:IdKon
  }

  return this.http.post(`${this.uri}/konverzacija/promeniStatus`,data);

}

dodajPoruku(IdKon,poruka){
  const data ={
    IdKon:IdKon,
    poruka:poruka
  }

  return this.http.post(`${this.uri}/konverzacija/dodajPoruku`,data);
}

updateCur(vlasnikCur,kupacCur,Cur,IdKon){
  const data ={
    IdKon:IdKon,
    vlasnikCur:vlasnikCur,
    kupacCur:kupacCur,
    Cur:Cur
  }

  return this.http.post(`${this.uri}/konverzacija/updateCur`,data);
}

blokiranKorisnik(kor1,kor2){
  const data ={
    kor1:kor1,
    kor2:kor2
  }

  return this.http.post(`${this.uri}/konverzacija/blokiranKorisnik`,data);

}

blokirajKorisnika(blokirao,blokiran){
  const data ={
    blokirao:blokirao,
    blokiran:blokiran
  }

  return this.http.post(`${this.uri}/konverzacija/blokirajKorisnika`,data);
}



blokiranaVeza(korisnik){
  const data ={
    kor1:korisnik
  }

  return this.http.post(`${this.uri}/konverzacija/blokiranaVeza`,data);
}
odblokiraj(k1,k2){
  const data ={
    kor1:k1,
    kor2:k2
  }

  return this.http.post(`${this.uri}/konverzacija/odblokiraj`,data);
}

updatePoruke(id,poruke){
  const data ={
    id:id,
    poruke:poruke
  }

  return this.http.post(`${this.uri}/konverzacija/updatePoruke`,data);
}


}
