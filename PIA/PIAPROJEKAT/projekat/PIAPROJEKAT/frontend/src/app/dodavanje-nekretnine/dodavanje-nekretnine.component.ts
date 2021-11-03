import { isNull } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, RequiredValidator, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { element } from 'protractor';
import { KorisnikService } from '../korisnik.service';
import Media from '../models/Media';
import User from '../models/User';
import { NekretninaService } from '../services/nekretnina.service';

@Component({
  selector: 'app-dodavanje-nekretnine',
  templateUrl: './dodavanje-nekretnine.component.html',
  styleUrls: ['./dodavanje-nekretnine.component.css']
})
export class DodavanjeNekretnineComponent implements OnInit {

  constructor(private usrServ:KorisnikService,
    private router:Router,
    private nekrServ:NekretninaService) { }

  ngOnInit(): void {
    this.user=JSON.parse(localStorage.getItem('user'));
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
      tipProdaje: new FormControl(null,Validators.required),
      cena: new FormControl(null,Validators.required),
      vlasnik: new FormControl(null,Validators.required),
      img1: new FormControl(null,Validators.required),
      img2: new FormControl(null,Validators.required),
      img3: new FormControl(null,Validators.required),
    })

    this.user=JSON.parse(localStorage.getItem('user'));
  
  }

  naziv:string;
  adresa:string;
  tip:string;
  opisTipa:string;
  kvadratura:number;
  brSoba:number;
  namestena:boolean;
  tipProdaje:string;
  cena:number;
  vlasnik:string;
  odobrena:boolean;
  user:User;
  form:FormGroup;

  poruka:string;
  failed:boolean;
  failedStan:boolean;


  username:string;
  porukaUsername:string;
  dodajNekretninu(){
    this.odobrena=false;
    if(this.user.tip!='agent'&&this.user.tip!='admin'){
      this.vlasnik=this.user.username;
    }else if(this.user.tip=='agent'){
      this.vlasnik="agencija";
      this.odobrena=true;
    }
    else if(this.user.tip=='admin'&&this.username!=null&&this.username!==""){
      this.vlasnik=this.username;
    }
    else if(this.username==null||this.username===""){
      this.porukaUsername='ovo polje je obavezno'
    }
    
    if(this.form.controls.naziv.errors||this.form.controls.grad.errors||this.form.controls.opstina.errors||this.form.controls.ulica.errors||this.form.controls.tip.errors||(this.form.value.tip=="kuca"&&this.form.controls.brSpratova.errors)||(this.form.value.tip=="stan"&&(0||this.form.controls.spratoviZgrade.errors))||this.form.controls.kvadratura.errors||this.form.controls.brSoba.errors||
    this.form.controls.namestena.errors||this.form.controls.tipProdaje.errors||this.form.controls.cena.errors||(this.videos.length+this.images.length<3)){  
      this.poruka="Ovo polje niste ispravno uneli";
      this.failed=true;
    }
    else if(this.form.value.tip=="stan"&&(this.form.value.naKomSpratu>this.form.value.spratoviZgrade||this.form.value.naKomSpratu<0||this.form.value.spratoviZgrade<0)){
      this.failedStan=true;
    }
    else if(this.porukaUsername){
      
    }
    else{
      this.poruka="";
      this.porukaUsername="";
      this.nekrServ.dodajNekretninu(this.form.value.naziv,this.form.value.grad,this.form.value.opstina,this.form.value.ulica,this.form.value.brojUlice,this.form.value.tip,this.form.value.brSpratova,this.form.value.naKomSpratu,this.form.value.spratoviZgrade,this.form.value.kvadratura,this.form.value.brSoba,this.form.value.namestena,this.form.value.tipProdaje,this.form.value.cena,this.vlasnik,this.odobrena).subscribe(res=>{
        if(res){
            console.log(res['message']);
            let tempId = res['generatedID'];

            for (let i = 0; i < this.images.length; i++) {
              const element = this.images[i];
              this.nekrServ.dodajSliku(tempId,element,this.vlasnik).subscribe(res=>{
                if(res){
                  console.log(res['message']);
                }
              })
            }

            for (let i = 0; i < this.videos.length; i++) {
              const element = this.videos[i];
              this.nekrServ.dodajVideo(tempId,element,this.vlasnik).subscribe(res=>{
                if(res){
                  console.log(res['message']);
                }
              })
            }


            this.form.reset();
            this.username=null;
            this.videos=[];
            this.images=[];
          }
        })
        this.failed=false;
        this.failedStan=false;
      }
  }



  videos:File[]=[];
  images:File[]=[];

  onFileSelect(event : Event){
    const file = (event.target as HTMLInputElement).files[0];
    const allowedImageTypes = ["image/png", "image/jpeg", "image/jpg"];
    const allowedVideoTypes = ["video/mp4"];
    if (file && allowedImageTypes.includes(file.type)) {
      this.images.push(file);
      console.log(this.images);
    }
    else if (file && allowedVideoTypes.includes(file.type)) {
      this.videos.push(file);
      console.log(this.videos);
    }
  }

  testVideos:string[]=[];
  testImages:string[]=[];

  test(){
    this.videos.forEach(element => {
      console.log("k");
    });
    this.images.forEach(element=>{
      console.log("d");
    })
    this.nekrServ.getAllMedia().subscribe((res:Media[])=>{
      res.forEach(element => {
        if(element.mediaType=="image"){
          this.testImages.push(element.mediaPath);
          console.log(element.mediaPath);
        }
        else if(element.mediaType=="video"){
          this.testVideos.push(element.mediaPath);
        }
      });
    })



  }
}
