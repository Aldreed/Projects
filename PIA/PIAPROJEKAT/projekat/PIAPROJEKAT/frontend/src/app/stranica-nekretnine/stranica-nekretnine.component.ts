
import { Component, OnInit } from '@angular/core';

import { Router, UrlSegment } from '@angular/router';
import Izdavanje from '../models/Izdavanje';
import Media from '../models/Media';
import NekretninaOmotac from '../models/NekretninaOmotac';
import Prodaja from '../models/Prodaja';
import User from '../models/User';
import { IzdavanjeService } from '../services/izdavanje.service';
import { NekretninaService } from '../services/nekretnina.service';
import { ProdajaService } from '../services/prodaja.service';
import {KonverzacijaService} from '../services/konverzacija.service'
import blokiraneVeze from '../models/blokiraneVeze';
import { JsonpClientBackend } from '@angular/common/http';

@Component({
  selector: 'app-stranica-nekretnine',
  templateUrl: './stranica-nekretnine.component.html',
  styleUrls: ['./stranica-nekretnine.component.css']
})
export class StranicaNekretnineComponent implements OnInit {

  constructor(private router: Router,
    private nekServ:NekretninaService,
    private izdServ:IzdavanjeService,
    private prodServ:ProdajaService,
    private konvServ:KonverzacijaService) {}

  ngOnInit(): void {

    
    
    let myNekretninaB=JSON.parse(localStorage.getItem('izabranaNekretnina'));
    this.user=JSON.parse(localStorage.getItem('user'));


    this.myNekretnina=new NekretninaOmotac();
    this.myNekretnina.nekretnina=myNekretninaB;
    //Get 
    this.nekServ.getMedia(this.myNekretnina.nekretnina.IdNek).subscribe((resp:Media[])=>{
      if(resp){
        console.log(this.myNekretnina.nekretnina.visits)
        this.myNekretnina.media=resp;
        let stringArr :number[]=[];
        if(localStorage.getItem('cookies')){
          stringArr=JSON.parse(localStorage.getItem('cookies'));
        }
        
        if(stringArr.indexOf(this.myNekretnina.nekretnina.IdNek)==-1){
          stringArr.push(this.myNekretnina.nekretnina.IdNek);
          this.myNekretnina.nekretnina.visits++;
          this.nekServ.updateVisits(this.myNekretnina.nekretnina.IdNek,this.myNekretnina.nekretnina.visits).subscribe(resp=>{
            if(resp){
              console.log('ok');
            }
          })
        }
        localStorage.setItem('cookies',JSON.stringify(stringArr));
        localStorage.setItem('izabranaNekretnina',JSON.stringify(this.myNekretnina.nekretnina));
      }
    })
    this.showSlides(1)
  }

  user:User;
  
  //Stan
  odDate:Date;
  doDate:Date;
  odDateNum:number;
  doDateNum:number;
  numMonths:string;

  //Kuca
  costMul:number=1.2;
  tipPlacanja:string;

  totalCost:number;

  pogresanDatumPoruka:string;

  prodajaPoruka:string;

  ucesce:number;
  ucesceKoef:number=0.2;

  placeOrderIzdavanje(){
    
    let user:User =JSON.parse(localStorage.getItem('user'));
    if(user.username==this.myNekretnina.nekretnina.vlasnik){
      this.porukaKonver="ovo je vaša nekretnina"
    }
    else if(this.odDate==null||this.doDate==null){
      this.pogresanDatumPoruka="niste ispravno uneli datum"
    }else{
      

    let datumOd:Date = new Date(this.odDate);
    let datumTo:Date = new Date(this.doDate);
    

    if((datumOd.getTime()>=datumTo.getTime())||((datumOd.getFullYear()==datumTo.getFullYear())&&(datumOd.getMonth()==datumTo.getMonth()))){
      this.pogresanDatumPoruka="niste ispravno uneli datum";
      this.totalCost=null;
    }
    else{
      let deltaD = datumTo.getTime()-datumOd.getTime();
      let dummyDate = new Date(deltaD);

      this.totalCost=(dummyDate.getMonth()+1)*this.myNekretnina.nekretnina.cena + (dummyDate.getFullYear()-1970)*12*this.myNekretnina.nekretnina.cena;
      console.log(this.totalCost);
      
      this.izdServ.getIzdavanja(this.myNekretnina.nekretnina.IdNek).subscribe((resp:Izdavanje[])=>{
        if(resp){
          let check:boolean=false;
          resp.forEach(element => {
            let elOd = new Date(element.datumOd);
            let elDo = new Date(element.datumDo);
            if(elOd.getTime()<=datumTo.getTime()&&elOd.getTime()>=datumOd.getTime()){
              check=true;
            }
            else if(elOd.getTime()<=datumOd.getTime()&&elDo.getTime()>=datumTo.getTime()){
              check=true;
            }
            else if(elDo.getTime()>=datumOd.getTime()&&elDo.getTime()<=datumTo.getTime()){
              check=true;
            }
          });
          console.log(check);
          if(check){
            this.pogresanDatumPoruka='Datum je zauzet';
          }
          else{
            this.pogresanDatumPoruka='';
          
            let kupac:User =JSON.parse(localStorage.getItem('user'));

            let odobreno =false;
            if(this.myNekretnina.nekretnina.vlasnik=="agencija"){
              odobreno=true;
            }

            this.izdServ.dodajIzdavanje(this.myNekretnina.nekretnina.IdNek,this.myNekretnina.nekretnina.vlasnik,kupac.username,datumOd,datumTo,this.totalCost,odobreno).subscribe(
              resp=>{
                if(resp){
                  console.log(resp);
                }
              }
            )
          }

        }
      })
    }
    }
  }


  placeOrderProdaja(){

    let user:User =JSON.parse(localStorage.getItem('user'));
    if(user.username==this.myNekretnina.nekretnina.vlasnik){
      this.porukaKonver="ovo je vaša nekretnina"
    }
    else if(this.tipPlacanja!=null){

      this.prodServ.getProdaja(this.myNekretnina.nekretnina.IdNek).subscribe((resp:Prodaja[])=>{
        if(resp.length>0){
          this.prodajaPoruka="nekretnina je prodata";
        }
        else if(resp.length==0){

          if(this.tipPlacanja=="gotovina"){
            this.totalCost=this.myNekretnina.nekretnina.cena;
          }
          else if(this.tipPlacanja=="kredit"){
            this.totalCost=this.myNekretnina.nekretnina.cena*this.costMul;
            this.ucesce=this.myNekretnina.nekretnina.cena*(this.costMul-1);
            this.ucesce=parseInt(this.ucesce.toFixed(2));
          }


          let kupac:User =JSON.parse(localStorage.getItem('user'));
  
              let odobeno =false;
              if(this.myNekretnina.nekretnina.vlasnik=="agencija"){
                odobeno=true;
              }
              
              this.prodServ.dodajProdaja(this.myNekretnina.nekretnina.IdNek,this.myNekretnina.nekretnina.vlasnik,kupac.username,this.totalCost,odobeno).subscribe(resp=>{
                if(resp){
                  console.log(resp);
                  this.prodajaPoruka="";
                }
              })
        }
      })
    }
    else{
      this.prodajaPoruka="nije unet nacin placanja";
    }
  }
 
  myNekretnina:NekretninaOmotac;

  cena:number;


  porukaKonver:string;
  pocniKonverzaciju(){

    let user:User =JSON.parse(localStorage.getItem('user'));
      if(user.username==this.myNekretnina.nekretnina.vlasnik){
        this.porukaKonver="ovo je vaša nekretnina"
      }else{
        this.konvServ.blokiranKorisnik(user.username,this.myNekretnina.nekretnina.vlasnik).subscribe((res:blokiraneVeze[])=>{
          if(res.length>0){
            this.porukaKonver="blokirali ste valsnika ove nekretnine";
          }
          else if(res.length==0){
            this.konvServ.blokiranKorisnik(this.myNekretnina.nekretnina.vlasnik,user.username).subscribe((res:blokiraneVeze[])=>{
              if(res.length>0){
                this.porukaKonver="korisnik vas je blokirao";
              }
              else{
                localStorage.setItem('konvVlasnik',JSON.stringify(this.myNekretnina.nekretnina.vlasnik));
                localStorage.setItem('konvKupac',JSON.stringify(user.username));
                localStorage.setItem('konvNekretnina',JSON.stringify(this.myNekretnina.nekretnina.naziv));
                localStorage.setItem('konvNekretninaID',JSON.stringify(this.myNekretnina.nekretnina.IdNek));
                this.router.navigate(['aktivnePoruke/konverzacija']);
              }     
              
            })
          }
        })
      }

    
  }

  slideIndex:number = 1;

  // Next/previous controls
 plusSlides(n) {
  this.showSlides(this.slideIndex += n);
}

// Thumbnail image controls
currentSlide(n) {
  this.showSlides(this.slideIndex = n);
}

showSlides(n) {
  var i;
  var slides:any[] = document.getElementsByClassName("mySlides") as unknown as HTMLDivElement[];
  var dots:any[] = document.getElementsByClassName("slideImage") as unknown as HTMLImageElement[];
  // var captionText = document.getElementById("caption");
  if (n > slides.length) {this.slideIndex = 1}
  if (n < 1) {this.slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  // for (i = 0; i < dots.length; i++) {
  //   dots[i].className = dots[i].className.replace(" active", "");
  // }
  slides[this.slideIndex-1].style.display = "block";
  //dots[this.slideIndex-1].className += " active";
  //  captionText.innerHTML = dots[this.slideIndex-1].alt;
} 


viewMedia:boolean=false;

prikaziMedije(){
  this.viewMedia=true;
  this.plusSlides(1);
}
}
