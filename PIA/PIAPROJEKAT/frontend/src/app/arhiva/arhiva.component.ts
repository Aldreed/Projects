import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import blokiiraneVeze from '../models/blokiraneVeze';
import Konverzacija from '../models/Konverzacija';
import User from '../models/User';
import { KonverzacijaService } from '../services/konverzacija.service';

@Component({
  selector: 'app-arhiva',
  templateUrl: './arhiva.component.html',
  styleUrls: ['./arhiva.component.css']
})
export class ArhivaComponent implements OnInit {

  constructor(private konvServ:KonverzacijaService,
    private router:Router) { }

  ngOnInit(): void {

    this.user=JSON.parse(localStorage.getItem('user'));

    let k1:string=this.user.username;
    if(this.user.tip=='agent'){
      k1='agencija';
    }

    this.konvServ.getArhiviranoKorisnik(k1).subscribe((resp:Konverzacija[])=>{
      if(resp){
        this.konv=resp;
      }
    })
  }


  konv:Konverzacija[]=[];
  user:User;
  poruka:string;

  aktiviraj(k:Konverzacija){

    let k1:string=this.user.username;
    let k2:string="";
    

    if(k.vlasnik==this.user.username||(k.vlasnik=='agencija'&&this.user.tip=='agent')){
      k2=k.kupac;
    }else{
      k2=k.vlasnik;
    }

    if(this.user.tip=='agent'){
      k1="agencija";
    }

    this.konvServ.blokiranKorisnik(k1,k2).subscribe((resp:blokiiraneVeze[])=>{
      if(resp.length>0){
        this.poruka="nije moguce aktivirati konverzaciju,blokirali ste korisnika"
      }
      else{
        this.konvServ.blokiranKorisnik(k2,k1).subscribe((resp:blokiiraneVeze[])=>{
          if(resp.length>0){
            this.poruka="nije moguce aktivirati konverzaciju,korisnik vas je blokirao"
          }
          else{
            this.konvServ.promeniStatus('aktivna',k.IdKon).subscribe((resp)=>{
              if(resp){
                console.log(resp['message']);
                this.konvServ.getArhiviranoKorisnik(k1).subscribe((resp:Konverzacija[])=>{
                  if(resp){
                    this.konv=resp;
    
                  }
                })
              }
            })
          }
        })
      }
    })
  }

  izaberiKonverzaciju(k:Konverzacija){

    let vla:number=null;
    let kup:number=null;


    if(this.user.username==k.vlasnik||(k.vlasnik=='agencija'&&this.user.tip=='agent')){
      vla=k.Cur;
      kup=k.kupacCur;
      k.vlasnikCur=k.Cur;
      
    }
    else{
      vla=k.vlasnikCur;
      kup=k.Cur;
      k.kupacCur=k.Cur;
    }

    this.konvServ.updateCur(vla,kup,k.Cur,k.IdKon).subscribe(resp=>{
      if(resp){
        console.log(resp['message']);
        k.Cur=k.Cur;
        
      }
    })

    localStorage.setItem('izabranaKonv',JSON.stringify(k));
    this.router.navigate(["aktivnePoruke/konverzacija"]);

  }

  helperDate(d:Date){
    let temp = new Date(d);
    return temp.toISOString().slice(0,10);
  }
}
