import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { from } from 'rxjs';
import Media from '../models/Media';
import Nekretnina from '../models/Nekretnina';
import User from '../models/User';
import { NekretninaService } from '../services/nekretnina.service';

@Component({
  selector: 'app-edit-nek',
  templateUrl: './edit-nek.component.html',
  styleUrls: ['./edit-nek.component.css']
})
export class EditNekComponent implements OnInit {

  constructor(private nekSer:NekretninaService) { }

  ngOnInit(): void {
    
    this.user=JSON.parse(localStorage.getItem('user'));
    this.nekSer.getNekretininaByVlasnik(this.user.username).subscribe((resp:Nekretnina[])=>{
      if(resp.length>0){
        this.nekretnine=resp;
      }
    })

    this.form=new FormGroup({
      naziv: new FormControl(null,Validators.required),
      grad: new FormControl(null,Validators.required),
      opstina: new FormControl(null,Validators.required),
      ulica: new FormControl(null,Validators.required),
      brojUlice: new FormControl(null,Validators.required),
      tip: new FormControl(null,Validators.required),
      brSpratova: new FormControl(null,Validators.required),
      naKomSpratu: new FormControl(null,Validators.required),
      spratoviZgrade: new FormControl(null,Validators.required),
      kvadratura: new FormControl(null,Validators.required),
      brSoba: new FormControl(null,Validators.required),
      namestena: new FormControl(null,Validators.required),
      cena: new FormControl(null,Validators.required),
      vlasnik: new FormControl(null,Validators.required),
      img: new FormControl(null),
    })
  
  }


  user:User;
  nekretnine:Nekretnina[];
  relatedMedia:Media[];

  izabranaNekretnina:Nekretnina;

  form:FormGroup;

  msgMarkers:boolean[]=[];
  izaberiNekrentinu(n:Nekretnina){
    this.nekSer.getMedia(n.IdNek).subscribe((resp:Media[])=>{
      if(resp){
        this.relatedMedia=resp;
        this.relatedMedia.forEach(element => {
          this.msgMarkers.push(false);
        });
        
        this.izabranaNekretnina=n;
        
      }
    })
  }

  porukaRemove:string;
  ukloniMedij(m:Media){
    if(this.relatedMedia.length==3){
      this.msgMarkers[this.relatedMedia.indexOf(m)]=true;
      this.porukaRemove="morate da imate barem tri medija";
    }
    else{
      for (let index = 0; index < this.relatedMedia.length; index++) {
        this.msgMarkers[index]=false;
      }
      if(m.mediaType=="image"){
        this.nekSer.removePic(m.mediaPath.slice(22)).subscribe((resp)=>{
          if(resp){
            this.nekSer.remove(m.IdMedia).subscribe(resp2=>{
              if(resp2){
                console.log("done");
              }
            })
          }
        })
      }
      else if(m.mediaType=="video"){
        this.nekSer.removeVid(m.mediaPath.slice(22)).subscribe((resp)=>{
          if(resp){
            this.nekSer.remove(m.IdMedia).subscribe(resp2=>{
              if(resp2){
                console.log("done");
              }
            })
          }
        })
      }
      this.relatedMedia.splice(this.relatedMedia.indexOf(m),1);
    }
  }

  pic:boolean=false;
  video:boolean=false;
  porukaInput:string;
  dodajMedij(){
    console.log(this.pic)
    console.log(this.video)
    if(this.form.value.img&&(this.pic||this.video)){
      if(this.pic){
        this.nekSer.dodajSliku(this.izabranaNekretnina.IdNek,this.form.value.img,this.user.username).subscribe(resp=>{
          if(resp){
            console.log(resp['message']);
            this.pic=false;
            this.video=false;
            this.form.value.img=null;
            this.nekSer.getMedia(this.izabranaNekretnina.IdNek).subscribe((resp:Media[])=>{
              if(resp){
                this.porukaInput="";
                this.relatedMedia=resp;
                this.relatedMedia.forEach(element => {
                  this.msgMarkers.push(false);
                });
              }
            })

          }
        })
      }
      else if(this.video){
        this.nekSer.dodajVideo(this.izabranaNekretnina.IdNek,this.form.value.img,this.user.username).subscribe(resp=>{
          if(resp){
            console.log(resp['message']);
            this.pic=false;
            this.video=false;
            this.form.value.img=null;
            this.nekSer.getMedia(this.izabranaNekretnina.IdNek).subscribe((resp:Media[])=>{
              if(resp){
                this.relatedMedia=resp;
                this.porukaInput="";
                this.relatedMedia.forEach(element => {
                  this.msgMarkers.push(false);
                });
              }
            })
          }
        })
      }
    }
    else{
      this.porukaInput="unesite fajl"
    }
  }

  onFileSelect(event : Event){
    const file = (event.target as HTMLInputElement).files[0];
    const allowedImageTypes = ["image/png", "image/jpeg", "image/jpg"];
    const allowedVideoTypes = ["video/mp4"];
    this.form.patchValue({ img: file });
    if (file && allowedImageTypes.includes(file.type)) {
      this.pic=true
      this.video=false;
    }
    else if (file && allowedVideoTypes.includes(file.type)) {
      this.video=true;
      this.pic=false;
    }
  }

  prekiniEdit(){
    this.izabranaNekretnina=null;
    this.relatedMedia=null;
    this.msgMarkers=[];
  }


  updateNek(){
    let naziv = this.izabranaNekretnina.naziv; 
    let grad= this.izabranaNekretnina.grad; 
    let opstina= this.izabranaNekretnina.opstina; 
    let ulica= this.izabranaNekretnina.ulica; 
    let brojUlice= this.izabranaNekretnina.brojUlice;
    let brSpratova= this.izabranaNekretnina.brSpratova; 
    let naKomSpratu= this.izabranaNekretnina.naKomSpratu; 
    let spratoviZgrade= this.izabranaNekretnina.spratoviZgrade;
    let kvadratura= this.izabranaNekretnina.kvadratura; 
    let brSoba= this.izabranaNekretnina.brSoba;
    let namestena= this.izabranaNekretnina.namestena;
    let cena= this.izabranaNekretnina.cena;

    if(this.form.value.naziv!=null&&!this.form.controls.naziv.errors){
      naziv=this.form.value.naziv;
    }
    console.log(naziv);

    if(this.form.value.grad!=null&&!this.form.controls.grad.errors){
      grad=this.form.value.grad;
    }

    if(this.form.value.opstina!=null&&!this.form.controls.opstina.errors){
      opstina=this.form.value.opstina;
    }

    if(this.form.value.ulica!=null&&!this.form.controls.ulica.errors){
      ulica=this.form.value.ulica;
    }

    if(this.form.value.brojUlice!=null&&!this.form.controls.brojUlice.errors){
      brojUlice=this.form.value.brojUlice;
    }
    
    if(this.form.value.brSpratova!=null&&!this.form.controls.brSpratova.errors){
      brSpratova=this.form.value.brSpratova;
    }
    
    if(this.form.value.naKomSpratu!=null&&!this.form.controls.naKomSpratu.errors){
      naKomSpratu=this.form.value.naKomSpratu;
    }
    
    if(this.form.value.spratoviZgrade!=null&&brSpratova<=this.form.value.spratoviZgrade&&!this.form.controls.spratoviZgrade.errors){
      spratoviZgrade=this.form.value.spratoviZgrade;
    }
    
    if(this.form.value.kvadratura!=null&&!this.form.controls.kvadratura.errors){
      kvadratura=this.form.value.kvadratura;
    }
    
    if(this.form.value.brSoba!=null&&!this.form.controls.brSoba.errors){
      brSoba=this.form.value.brSoba;
    }
    
    if(this.form.value.namestena!=null&&!this.form.controls.name.errors){
      namestena=this.form.value.namestena;
    }
    
    if(this.form.value.cena!=null&&!this.form.controls.cena.errors){
      cena=this.form.value.cena;
    }

    this.nekSer.updateNekretninu(this.izabranaNekretnina.IdNek,naziv,grad,opstina,ulica,brojUlice,brSpratova,naKomSpratu,spratoviZgrade,kvadratura,brSoba,namestena,this.izabranaNekretnina.tipProdaje,cena).subscribe(resp=>{
      if(resp){
        this.nekSer.getNekretininaByVlasnik(this.user.username).subscribe((resp:Nekretnina[])=>{
          if(resp.length>0){
            this.nekretnine=resp;
            this.form.value.naziv=null;
            this.form.value.grad=null;
            this.form.value.opstina=null;
            this.form.value.ulica=null;
            this.form.value.brojUlice=null;
            this.form.value.brSpratova=null;
            this.form.value.naKomSpratu=null;
            this.form.value.spratoviZgrade=null;
            this.form.value.kvadratura=null;
            this.form.value.brSoba=null;
            this.form.value.namestena=null;
            this.form.value.cena=null;
            this.form.reset();
            this.prekiniEdit();
          }
        })
      }
    })
  }
    

}
