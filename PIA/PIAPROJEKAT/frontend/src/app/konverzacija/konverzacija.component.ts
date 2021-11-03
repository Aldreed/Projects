import { Component, OnInit } from '@angular/core';
import { ITS_JUST_ANGULAR } from '@angular/core/src/r3_symbols';
import { Router, UrlSegment } from '@angular/router';
import { Agent } from 'http';
import blokiiraneVeze from '../models/blokiraneVeze';
import Izdavanje from '../models/Izdavanje';
import Konverzacija from '../models/Konverzacija';
import Nekretnina from '../models/Nekretnina';
import NekretninaOmotac from '../models/NekretninaOmotac';
import Poruka from '../models/Poruka';
import Prodaja from '../models/Prodaja';
import User from '../models/User';
import { IzdavanjeService } from '../services/izdavanje.service';
import { KonverzacijaService } from '../services/konverzacija.service';
import { NekretninaService } from '../services/nekretnina.service';
import { ProdajaService } from '../services/prodaja.service';

@Component({
  selector: 'app-konverzacija',
  templateUrl: './konverzacija.component.html',
  styleUrls: ['./konverzacija.component.css']
})
export class KonverzacijaComponent implements OnInit {

  constructor(private konvServ:KonverzacijaService,
    private router:Router,
    private nekServ:NekretninaService,
    private izdServ:IzdavanjeService,
    private prodServ:ProdajaService) { }

  ngOnInit(): void {

    if(localStorage.getItem('konvVlasnik')){
      let vlasnik:string =JSON.parse(localStorage.getItem('konvVlasnik'));
      let kupac:string =JSON.parse(localStorage.getItem('konvKupac'));
      let naziv:string =JSON.parse(localStorage.getItem('konvNekretnina'));
      let IdNek:number =JSON.parse(localStorage.getItem('konvNekretninaID'));
    
      localStorage.removeItem('konvVlasnik');
      localStorage.removeItem('konvKupac');
      localStorage.removeItem('konvNekretnina');
      localStorage.removeItem('konvNekretninaID');
      
      this.konv=new Konverzacija();
      this.konv.IdNek=IdNek;
      this.konv.vlasnik=vlasnik;
      this.konv.kupac=kupac;
      this.konv.naziv=naziv;
      this.konv.poruke=[];
      this.konv.Cur=1;
      this.konv.vlasnikCur=1;
      this.konv.kupacCur=1;
      this.firstMessage=true;
    }
    else if(localStorage.getItem('izabranaKonv')){
      this.konv =JSON.parse(localStorage.getItem('izabranaKonv'));
      this.firstMessage=false
      localStorage.removeItem('izabranaKonv');

    }
    this.user = JSON.parse(localStorage.getItem('user'));

    let k1:string=this.user.username;
    let k2:string="";
    

    if(this.konv.vlasnik==this.user.username||(this.konv.vlasnik=='agencija'&&this.user.tip=='agent')){
      k2=this.konv.kupac;
    }else{
      k2=this.konv.vlasnik;
    }

    if(this.user.tip=='agent'){
      k1="agencija";
    }

    this.konvServ.blokiranKorisnik(k1,k2).subscribe((resp:blokiiraneVeze[])=>{
      if(resp.length>0){
        this.blockedKonvo=true;
        this.blokirao=true;
      }
      else{
        this.konvServ.blokiranKorisnik(k2,k1).subscribe((resp2:blokiiraneVeze[])=>{
          if(resp2.length>0){
            this.blockedKonvo=true;
            this.blokirao=false;
            console.log('ok');
          }
          else{
            this.blockedKonvo=false;
            this.blokirao=false; 
          }
        })
      }
    })

    this.nekServ.getById(this.konv.IdNek).subscribe((resp:Nekretnina[])=>{
      if(resp.length>0){
        this.myNekretnina=new NekretninaOmotac();
        this.myNekretnina.nekretnina=resp[0];
      }
    })

    

  }
  user:User;
  blockedKonvo:boolean=false;
  blokirao:boolean=false;
  konv:Konverzacija=null;
  firstMessage:boolean;
  poruke:string[];

  tekstPoruke:string;

  dodajProuku(){
    if(this.firstMessage&&this.tekstPoruke!=null){
      let novaPoruka:Poruka = new Poruka();
      novaPoruka.sender=this.user.username;
      novaPoruka.poruka=this.tekstPoruke;
      novaPoruka.time= Date.now();
      novaPoruka.tipPoruke='tekst';
      novaPoruka.tipUser=this.user.tip;
      this.konv.poslednjaPoruka=new Date(novaPoruka.time);
      this.konv.poruke.push(novaPoruka);
      this.firstMessage=false;
      
      this.konvServ.dodajKonverzaciju(this.konv.IdNek,this.konv.naziv,this.konv.vlasnik,this.konv.kupac,'aktivna',this.konv.poslednjaPoruka,this.konv.poruke).subscribe(resp=>{
        if(resp){
          console.log(resp['message'])
          this.konv.IdKon=resp['IdGen'];
          this.tekstPoruke="";

          
          let vla:number=null;
          let kup:number=null;


          let k1:string=this.user.username;
          if(this.user.tip=='agent'){
            k1='agencija'
          }

          if(k1==this.konv.vlasnik){
            vla=1;
            kup=0;
          }
          else{
            vla=0;
            kup=1;
          }

          this.konvServ.updateCur(vla,kup,1,this.konv.IdKon).subscribe(resp=>{
            if(resp){
              console.log(resp['message']);

            }
          })
        }
      })
      
    }
    else if(this.tekstPoruke!=null){
      let novaPoruka:Poruka = new Poruka();
      novaPoruka.sender=this.user.username;
      novaPoruka.poruka=this.tekstPoruke;
      novaPoruka.time= Date.now();
      novaPoruka.tipPoruke='tekst';
      novaPoruka.tipUser=this.user.tip;
      this.konv.poslednjaPoruka=new Date(novaPoruka.time);
      this.konv.poruke.push(novaPoruka);
      this.firstMessage=false;

      this.konvServ.dodajPoruku(this.konv.IdKon,novaPoruka).subscribe(resp=>{
        console.log(resp)
        this.tekstPoruke="";

        let vla:number=null;
        let kup:number=null;

        let k1:string=this.user.username;
        if(this.user.tip=='agent'){
          k1='agencija'
        }

        if(k1==this.konv.vlasnik){
          vla=this.konv.Cur+1;
          kup=this.konv.kupacCur;
          this.konv.vlasnikCur=this.konv.Cur+1;
          
        }
        else{
          vla=this.konv.vlasnikCur;
          kup=this.konv.Cur+1;
          this.konv.kupacCur=this.konv.Cur+1;
        }

        this.konvServ.updateCur(vla,kup,this.konv.Cur+1,this.konv.IdKon).subscribe(resp=>{
          if(resp){
            console.log(resp['message']);
            this.konv.Cur=this.konv.Cur+1;
          }

        })


      })
    }
  }

  blokiraj(){

    if(this.konv.vlasnik!='agencija'||this.user.tip=="agent"){

    this.blockedKonvo=true;
    this.blokirao=true;

    let k1:string=this.user.username;
    let k2:string="";
    

    if(this.konv.vlasnik==this.user.username||(this.konv.vlasnik=='agencija'&&this.user.tip=='agent')){
      k2=this.konv.kupac;
    }else{
      k2=this.konv.vlasnik;
    }

    if(this.user.tip=='agent'){
      k1="agencija";
    }

    this.konvServ.blokirajKorisnika(k1,k2).subscribe(resp=>{
      if(resp){
        console.log(resp)
        this.konvServ.promeniStatus('arhivirana',this.konv.IdKon).subscribe(resp2=>{
          if(resp2){
            console.log(resp2);
            this.router.navigate(["aktivnePoruke"]);
          }
        })
      }
    })
    }
  
  }

  odblokiraj(){
    this.blokirao=false;

    let k2:string="";
    let k1:string=this.user.username;

    if(this.konv.vlasnik==this.user.username||(this.konv.vlasnik=='agencija'&&this.user.tip=='agent')){
      k2=this.konv.kupac;
    }else{
      k2=this.konv.vlasnik;
    }

    if(this.user.tip=='agent'){
      k1="agencija";
    }


    this.konvServ.odblokiraj(k1,k2).subscribe(resp=>{
      if(resp){
        console.log(resp['message']);
        this.konvServ.blokiranKorisnik(k2,this.user.username).subscribe((resp2:blokiiraneVeze[])=>{
          if(resp2.length==0){
            this.blockedKonvo=false;
          }
        })
      }
    })

  }


  //PONUDA
  myNekretnina:NekretninaOmotac=null;
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

  cena:number;


  ponudaIzdavanje(){
    this.pogresanDatumPoruka="";
    if(typeof(this.cena) !== "number"){
      console.log("nije broj");
    }
    else if(this.odDate==null||this.doDate==null){
      this.pogresanDatumPoruka="niste ispravno uneli datum";
      this.totalCost=null;
      console.log("no");
    }
    else if(this.cena==null){
      this.pogresanDatumPoruka="niste ispravno uneli cenu";
      this.totalCost=null;
      console.log("no");
    }
    
    else{
      

    let datumOd:Date = new Date(this.odDate);
    let datumTo:Date = new Date(this.doDate);
    

    if((datumOd.getTime()>=datumTo.getTime())||((datumOd.getFullYear()==datumTo.getFullYear())&&(datumOd.getMonth()==datumTo.getMonth()))){
      this.pogresanDatumPoruka="niste ispravno uneli datum";
      this.totalCost=null;
      console.log("no");
    }
    else{
      let deltaD = datumTo.getTime()-datumOd.getTime();
      let dummyDate = new Date(deltaD);

      this.totalCost=(dummyDate.getMonth()+1)*this.cena + (dummyDate.getFullYear()-1970)*12*this.cena;
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
            this.totalCost=null;
          }
          else{
            this.pogresanDatumPoruka='';
          
            let kupac:User =JSON.parse(localStorage.getItem('user'));

            if(this.firstMessage&&this.tekstPoruke!=null){
              let novaPoruka:Poruka = new Poruka();
              novaPoruka.sender=this.user.username;
              novaPoruka.poruka=this.tekstPoruke;
              novaPoruka.time= Date.now();

              novaPoruka.tipPoruke='ponudaIzdavanje';
              novaPoruka.cena=this.cena;
              novaPoruka.datumOd=datumOd.toISOString().slice(0,10);
              novaPoruka.datumTo=datumTo.toISOString().slice(0,10);
              
              novaPoruka.tipUser=this.user.tip;
              this.konv.poslednjaPoruka=new Date(novaPoruka.time);
              this.konv.poruke.push(novaPoruka);
              this.firstMessage=false;
              
              this.konvServ.dodajKonverzaciju(this.konv.IdNek,this.konv.naziv,this.konv.vlasnik,this.konv.kupac,'aktivna',this.konv.poslednjaPoruka,this.konv.poruke).subscribe(resp=>{
                if(resp){
                  console.log(resp['message'])
                  this.konv.IdKon=resp['IdGen'];
                  this.tekstPoruke="";
        
                  
                  let vla:number=null;
                  let kup:number=null;
        
        
                  let k1:string=this.user.username;
                  if(this.user.tip=='agent'){
                    k1='agencija'
                  }
        
                  if(k1==this.konv.vlasnik){
                    vla=1;
                    kup=0;
                  }
                  else{
                    vla=0;
                    kup=1;
                  }
        
                  this.konvServ.updateCur(vla,kup,1,this.konv.IdKon).subscribe(resp=>{
                    if(resp){
                      console.log(resp['message']);
        
                    }
                  })
                }
              })
              
            }
            else{
              let novaPoruka:Poruka = new Poruka();
              novaPoruka.sender=this.user.username;
              novaPoruka.poruka=this.tekstPoruke;
              novaPoruka.time= Date.now();

              novaPoruka.tipPoruke='ponudaIzdavanje';
              novaPoruka.cena=this.cena;
              novaPoruka.datumOd=datumOd.toISOString().slice(0,10);
              novaPoruka.datumTo=datumTo.toISOString().slice(0,10);
              

              novaPoruka.tipUser=this.user.tip;
              this.konv.poslednjaPoruka=new Date(novaPoruka.time);
              this.konv.poruke.push(novaPoruka);
              this.firstMessage=false;
        
              this.konvServ.dodajPoruku(this.konv.IdKon,novaPoruka).subscribe(resp=>{
                console.log(resp)
                this.tekstPoruke="";
        
                let vla:number=null;
                let kup:number=null;
        
                let k1:string=this.user.username;
                if(this.user.tip=='agent'){
                  k1='agencija'
                }
        
                if(k1==this.konv.vlasnik){
                  vla=this.konv.Cur+1;
                  kup=this.konv.kupacCur;
                  this.konv.vlasnikCur=this.konv.Cur+1;
                  
                }
                else{
                  vla=this.konv.vlasnikCur;
                  kup=this.konv.Cur+1;
                  this.konv.kupacCur=this.konv.Cur+1;
                }
        
                this.konvServ.updateCur(vla,kup,this.konv.Cur+1,this.konv.IdKon).subscribe(resp=>{
                  if(resp){
                    console.log(resp['message']);
                    this.konv.Cur=this.konv.Cur+1;
                  }
        
                })
        
        
              })
            }

           
          }

        }
      })
    }
    }

  }

  ponudaProdaja(){
    if(this.tipPlacanja!=null){


      this.prodServ.getProdaja(this.myNekretnina.nekretnina.IdNek).subscribe((resp:Prodaja[])=>{
        if(resp.length>0){
          this.prodajaPoruka="nekretnina je prodata";
        }
        else if(resp.length==0){

          if(this.tipPlacanja=="gotovina"){
            this.totalCost=this.cena;
          }
          else if(this.tipPlacanja=="kredit"){
            this.totalCost=this.cena*this.costMul;
            this.ucesce=this.myNekretnina.nekretnina.cena*(this.costMul-1);
            this.ucesce=parseInt(this.ucesce.toFixed(2));
          }


          let kupac:User =JSON.parse(localStorage.getItem('user'));
          if(this.firstMessage&&this.tekstPoruke!=null){
            let novaPoruka:Poruka = new Poruka();
            novaPoruka.sender=this.user.username;
            novaPoruka.poruka=this.tekstPoruke;
            novaPoruka.time= Date.now();

            novaPoruka.tipPoruke='ponudaProdaja';
            novaPoruka.ucesce=this.ucesce;
            novaPoruka.cena=this.totalCost;

            novaPoruka.tipUser=this.user.tip;
            this.konv.poslednjaPoruka=new Date(novaPoruka.time);
            this.konv.poruke.push(novaPoruka);
            this.firstMessage=false;
            
            this.konvServ.dodajKonverzaciju(this.konv.IdNek,this.konv.naziv,this.konv.vlasnik,this.konv.kupac,'aktivna',this.konv.poslednjaPoruka,this.konv.poruke).subscribe(resp=>{
              if(resp){
                console.log(resp['message'])
                this.konv.IdKon=resp['IdGen'];
                this.tekstPoruke="";
      
                
                let vla:number=null;
                let kup:number=null;
      
      
                let k1:string=this.user.username;
                if(this.user.tip=='agent'){
                  k1='agencija'
                }
      
                if(k1==this.konv.vlasnik){
                  vla=1;
                  kup=0;
                }
                else{
                  vla=0;
                  kup=1;
                }
      
                this.konvServ.updateCur(vla,kup,1,this.konv.IdKon).subscribe(resp=>{
                  if(resp){
                    console.log(resp['message']);
      
                  }
                })
              }
            })
            
          }
          else{
            let novaPoruka:Poruka = new Poruka();
            novaPoruka.sender=this.user.username;
            novaPoruka.poruka=this.tekstPoruke;
            novaPoruka.time= Date.now();

            novaPoruka.tipPoruke='ponudaProdaja';
            novaPoruka.ucesce=this.ucesce;
            novaPoruka.cena=this.totalCost;

            novaPoruka.tipUser=this.user.tip;
            this.konv.poslednjaPoruka=new Date(novaPoruka.time);
            this.konv.poruke.push(novaPoruka);
            this.firstMessage=false;
      
            this.konvServ.dodajPoruku(this.konv.IdKon,novaPoruka).subscribe(resp=>{
              console.log(resp)
              this.tekstPoruke="";
      
              let vla:number=null;
              let kup:number=null;
      
              let k1:string=this.user.username;
              if(this.user.tip=='agent'){
                k1='agencija'
              }
      
              if(k1==this.konv.vlasnik){
                vla=this.konv.Cur+1;
                kup=this.konv.kupacCur;
                this.konv.vlasnikCur=this.konv.Cur+1;
                
              }
              else{
                vla=this.konv.vlasnikCur;
                kup=this.konv.Cur+1;
                this.konv.kupacCur=this.konv.Cur+1;
              }
      
              this.konvServ.updateCur(vla,kup,this.konv.Cur+1,this.konv.IdKon).subscribe(resp=>{
                if(resp){
                  console.log(resp['message']);
                  this.konv.Cur=this.konv.Cur+1;
                }
      
              })
      
      
            })
          }
              
        }
      })
    }
    else{
      this.prodajaPoruka="nije unet nacin placanja";
    }
  }
  

  placeOrderIzdavanje(poruka:Poruka){
    
    if(poruka.datumOd==null||poruka.datumTo==null){
      this.pogresanDatumPoruka="niste ispravno uneli datum"
    }else{
      

    let datumOd:Date = new Date(poruka.datumOd);
    let datumTo:Date = new Date(poruka.datumTo);
    

    if((datumOd.getTime()>=datumTo.getTime())||((datumOd.getFullYear()==datumTo.getFullYear())&&(datumOd.getMonth()==datumTo.getMonth()))){
      this.pogresanDatumPoruka="niste ispravno uneli datum";
      this.totalCost=null;
    }
    else{
      let deltaD = datumTo.getTime()-datumOd.getTime();
      let dummyDate = new Date(deltaD);

      this.totalCost=(dummyDate.getMonth()+1)*poruka.cena + (dummyDate.getFullYear()-1970)*12*poruka.cena;
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
            poruka.prihvacena="odbijena";
            this.konvServ.updatePoruke(this.konv.IdKon,this.konv.poruke).subscribe(resp=>{
              if(resp){
                console.log('updatedPor');
              }
            })
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
                  poruka.prihvacena="prihvacena";
                  this.rejectAll();
                  this.konvServ.updatePoruke(this.konv.IdKon,this.konv.poruke).subscribe(resp=>{
                    if(resp){
                      console.log("ok");
                      if(this.user.tip=="agent"){
                        this.izdServ.getNeodobrenaByIDNek(this.konv.IdNek).subscribe((izdavanjaNeodobrena:Izdavanje[])=>{
                          if(izdavanjaNeodobrena){
                            console.log("here");
                            izdavanjaNeodobrena.forEach((element,index) => {
                              if(element.IdNek==this.konv.IdNek){
                                let elOd = new Date(element.datumOd);
                                let elDo = new Date(element.datumDo);
                                let datumTo=new Date(poruka.datumOd);
                                let datumOd=new Date(poruka.datumTo);
                                let check:boolean=false;
                                if(elOd.getTime()<=datumTo.getTime()&&elOd.getTime()>=datumOd.getTime()){
                                  check=true;
                                }
                                else if(elOd.getTime()<=datumOd.getTime()&&elDo.getTime()>=datumTo.getTime()){
                                  check=true;
                                }
                                else if(elDo.getTime()>=datumOd.getTime()&&elDo.getTime()<=datumTo.getTime()){
                                  check=true;
                                }
                    
                                if(check){
                                  this.izdServ.odbaci(element.IdIzd).subscribe(resp=>{
                                    if(resp){
                                      console.log('removed');
                                    }
                                  })
                                }
                              }
                            });
                          }
                        })
                      }
                    }
                  })
                }
              }
            )
          }

        }
      })
    }
    }
  }


  placeOrderProdaja(poruka:Poruka){

      this.prodServ.getProdaja(this.myNekretnina.nekretnina.IdNek).subscribe((resp:Prodaja[])=>{
        if(resp.length>0){
          this.prodajaPoruka="nekretnina je prodata";
        }
        else if(resp.length==0){

          // if(this.tipPlacanja=="gotovina"){
          //   this.totalCost=poruka.cena;
          // }
          // else if(this.tipPlacanja=="kredit"){
          //   this.totalCost=poruka.cena*this.costMul;
          //   poruka.ucesce=this.myNekretnina.nekretnina.cena*(this.costMul-1);
          //   poruka.ucesce=parseInt(this.ucesce.toFixed(2));
          // }


          let kupac:User =JSON.parse(localStorage.getItem('user'));
  
              let odobeno =false;
              if(this.myNekretnina.nekretnina.vlasnik=="agencija"){
                odobeno=true;
              }
              
              this.prodServ.dodajProdaja(this.myNekretnina.nekretnina.IdNek,this.myNekretnina.nekretnina.vlasnik,kupac.username,poruka.cena,odobeno).subscribe(resp=>{
                if(resp){
                  console.log(resp);
                  this.prodajaPoruka="";
                  poruka.prihvacena="prihvacena";
                  this.rejectAll();
                  this.konvServ.updatePoruke(this.konv.IdKon,this.konv.poruke).subscribe(resp=>{
                    if(resp){
                      console.log("ok");
                      if(this.user.tip=="agent"){
                        this.prodServ.odbaci(this.konv.IdNek).subscribe(resp2=>{
                          if(resp2){
                            console.log("removed");
                          }
                        })
                      }
                    }
                  })
                }
              })
        }
      })
  }

  odbijPonudu(poruka:Poruka){
    poruka.prihvacena="odbijena";
  }

  //UPDATE PORUKA
  rejectAll(){
    this.konv.poruke.forEach(element => {
      if(element.tipPoruke!="tekst"&&element.prihvacena=="cekanje"){
        element.prihvacena="odbijena"
      }
    });
  }

  nazad(){
    if(this.konv.status=="arhivirana"){
      this.router.navigate(['aktivnePoruke/arhiva']);
    }
    else if(this.konv.status=="aktivna"){
      this.router.navigate(['aktivnePoruke']);

    }
    else{
      this.router.navigate(['aktivnePoruke']);
    }
  }

}
