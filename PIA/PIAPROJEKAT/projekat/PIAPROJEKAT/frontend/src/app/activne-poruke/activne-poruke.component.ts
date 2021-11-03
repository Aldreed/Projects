import { utf8Encode } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Konverzacija from '../models/Konverzacija';
import Poruka from '../models/Poruka';
import User from '../models/User';
import { KonverzacijaService } from '../services/konverzacija.service';

@Component({
  selector: 'app-activne-poruke',
  templateUrl: './activne-poruke.component.html',
  styleUrls: ['./activne-poruke.component.css']
})
export class ActivnePorukeComponent implements OnInit {

  constructor(private konvServ:KonverzacijaService,
    private router:Router) { }

  ngOnInit(): void {
  
    this.user = JSON.parse(localStorage.getItem('user'));

    let k1:string=this.user.username;
    if(this.user.tip=='agent'){
      k1='agencija';
    }
    this.konvServ.getKonverzacijaKorisnik(k1).subscribe((resp:Konverzacija[])=>{
      if(resp){
        this.konv=resp;
      }
    })

  }

    konv:Konverzacija[]=[];
    user:User;


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

    arhiviraj(k:Konverzacija){

      let k1:string = this.user.username;
      if(this.user.tip=='agent'){
        k1='agencija';
      }

      this.konvServ.promeniStatus('arhivirana',k.IdKon).subscribe(resp=>{
        if(resp){
          this.konvServ.getKonverzacijaKorisnik(k1).subscribe((resp:Konverzacija[])=>{
            if(resp){
              this.konv=resp;
            }
          })
        }
      })
    }


    helperDate(d:Date){
      let temp = new Date(d);
      return temp.toISOString().slice(0,10);
    }

    izabranaKonvNum:number=null;
    tekstPoruke:string;
    izabranaKonv:Konverzacija;
    dodajPoruku(){
      if(this.izabranaKonvNum!=null){
        this.izabranaKonv=this.konv[this.izabranaKonvNum];
        console.log(this.izabranaKonv);
        let novaPoruka:Poruka = new Poruka();
        novaPoruka.sender=this.user.username;
        novaPoruka.poruka=this.tekstPoruke;
        novaPoruka.time= Date.now();
        novaPoruka.tipPoruke='tekst';
        novaPoruka.tipUser=this.user.tip;
        this.izabranaKonv.poslednjaPoruka=new Date(novaPoruka.time);
        this.izabranaKonv.poruke.push(novaPoruka);
  
        this.konvServ.dodajPoruku(this.izabranaKonv.IdKon,novaPoruka).subscribe(resp=>{
          console.log(resp)
          this.tekstPoruke="";
  
          let vla:number=null;
          let kup:number=null;
  
          let k1:string=this.user.username;
          if(this.user.tip=='agent'){
            k1='agencija'
          }
  
          if(k1==this.izabranaKonv.vlasnik){
            vla=this.izabranaKonv.Cur+1;
            kup=this.izabranaKonv.kupacCur;
            this.izabranaKonv.vlasnikCur=this.izabranaKonv.Cur+1;
            
          }
          else{
            vla=this.izabranaKonv.vlasnikCur;
            kup=this.izabranaKonv.Cur+1;
            this.izabranaKonv.kupacCur=this.izabranaKonv.Cur+1;
          }
  
          this.konvServ.updateCur(vla,kup,this.izabranaKonv.Cur+1,this.izabranaKonv.IdKon).subscribe(resp=>{
            if(resp){
              console.log(resp['message']);
              this.izabranaKonv.Cur=this.izabranaKonv.Cur+1;
            }
  
          })
  
  
        })
      }
    }
}
