import { Component, OnInit } from '@angular/core';
import Izdavanje from '../models/Izdavanje';
import Nekretnina from '../models/Nekretnina';
import Prodaja from '../models/Prodaja';
import { IzdavanjeService } from '../services/izdavanje.service';
import { NekretninaService } from '../services/nekretnina.service';
import { ProdajaService } from '../services/prodaja.service';

@Component({
  selector: 'app-agent-window',
  templateUrl: './agent-window.component.html',
  styleUrls: ['./agent-window.component.css']
})
export class AgentWindowComponent implements OnInit {

  constructor(private nekrServ:NekretninaService,
    private izdvServ:IzdavanjeService,
    private prodServ:ProdajaService) { }

  ngOnInit(): void {
    this.getListe();
  }


  featured:Nekretnina[];
  notFeatured:Nekretnina[];
  neodobrene:Nekretnina[];

  prodajeNeodobrene:Prodaja[];
  izdavanjaNeodobrena:Izdavanje[];

  odobreneProdaje:Prodaja[];
  odobrenaIzdavanja:Izdavanje[];

  feature(n:Nekretnina){
    this.nekrServ.feature(n.IdNek).subscribe(resp=>{
      if(resp){
        console.log("ok");
        this.getListe();
      }
    })
  }

  
  featureStop(n:Nekretnina){
    this.nekrServ.featureStop(n.IdNek).subscribe(resp=>{
      if(resp){
        console.log("ok");
        this.getListe();
      }
    })
  }

  odobri(n:Nekretnina){
    this.nekrServ.odobri(n.IdNek).subscribe(resp=>{
      if(resp){
        console.log("ok");
        this.getListe();
      }
    })
  }


  odobriP(p:Prodaja){
    this.prodServ.odobri(p.IdPro).subscribe(resp=>{
      if(resp){
        console.log("ok");
        this.prodServ.odbaci(p.IdNek).subscribe(resp2=>{
          if(resp2){
            this.getListe();
          }
        })
      }
    })
  }

  odobriI(i:Izdavanje){
    this.izdvServ.odobri(i.IdIzd).subscribe(resp=>{
      if(resp){
        console.log("ok");
        this.izdavanjaNeodobrena.forEach((element,index) => {
          if(element.IdNek==i.IdNek){
            let elOd = new Date(element.datumOd);
            let elDo = new Date(element.datumDo);
            let datumTo=new Date(i.datumOd);
            let datumOd=new Date(i.datumDo);
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
              this.izdvServ.odbaci(element.IdIzd).subscribe(resp=>{
                if(resp){
                  console.log('removed');
                  this.izdavanjaNeodobrena.splice(index,1);//assignment ?
                }
              })
            }
          }
        });
        this.getListe();
      }
    })
  }



  getListe(){
    
    this.nekrServ.getNeodobrene().subscribe((resp:Nekretnina[])=>{
      if(resp){
        this.neodobrene=resp;
        this.nekrServ.getFeaturedNekretina().subscribe((resp1:Nekretnina[])=>{
          if(resp1){
            this.featured=resp1;
            this.nekrServ.getNotFeatured().subscribe((resp2:Nekretnina[])=>{
              if(resp2){
                this.notFeatured=resp2;
                this.prodServ.getNeodobrene().subscribe((resp3:Prodaja[])=>{
                  if(resp3){
                    this.prodajeNeodobrene=resp3;
                    this.izdvServ.getNeodobrene().subscribe((resp3:Izdavanje[])=>{
                      if(resp3){
                        this.izdavanjaNeodobrena=resp3;
                        this.prodServ.getOdobrene().subscribe((resp4:Prodaja[])=>{
                          if(resp4){
                            this.odobreneProdaje=resp4;
                            this.izdvServ.getOdobrena().subscribe((resp5:Izdavanje[])=>{
                              if(resp5){
                                this.odobrenaIzdavanja=resp5;
                                this.calculate();
                              }
                            })
                          }
                        })
                      }
                    })
                  }
                })
              }
            })
          }
        })
      }
    })
  }


  totalIzdavanje:number=0;
  totalProdaje:number=0;
  total:number=0;
  calculate(){
    this.odobrenaIzdavanja.forEach(element => {
      if(element.vlasnik!="agencija"){
        this.totalIzdavanje+=element.totalCost*element.procenat;
      }
      else{
        this.totalIzdavanje+=element.totalCost;
      }
    });
    this.odobreneProdaje.forEach(element => {
      if(element.vlasnik!="agencija"){
        this.totalProdaje+=element.totalCost*element.procenat;
      }
      else{
        this.totalProdaje+=element.totalCost;
      }
    });

    this.total=this.totalIzdavanje+this.totalProdaje;
    
  }
}
