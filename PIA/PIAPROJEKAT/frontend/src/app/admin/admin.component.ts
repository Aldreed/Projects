import { KeyedRead } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NumberValueAccessor, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { from } from 'rxjs';
import { KorisnikService } from '../korisnik.service';
import Izdavanje from '../models/Izdavanje';
import Nekretnina from '../models/Nekretnina';
import Prodaja from '../models/Prodaja';
import User from '../models/User';
import { IzdavanjeService } from '../services/izdavanje.service';
import { NekretninaService } from '../services/nekretnina.service';
import { ProdajaService } from '../services/prodaja.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(private korServ:KorisnikService,
    private nekServ:NekretninaService,
    private izdServ:IzdavanjeService,
    private prodServ:ProdajaService,
    private router:Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      ime: new FormControl(null,Validators.required),
      prezime: new FormControl(null,Validators.required),
      username: new FormControl(null,Validators.required),
      email: new FormControl(null,[Validators.pattern("[^ @]*@[^ @]*"),Validators.required]),
      grad: new FormControl(null,Validators.required),
      drzava: new FormControl(null,Validators.required),
      tip: new FormControl(null,Validators.required),
      image: new FormControl(null)
    })

    this.reloadTimestamp();
    this.korServ.getUserS().subscribe((resp:User[])=>{
      if(resp){
        this.odobreniKorisnici=resp;
        this.nekServ.getNeodobrene().subscribe((resp2:Nekretnina[])=>{
          if(resp2){
            this.neodobreneNekretnine=resp2;
            this.izdServ.getOdobrena().subscribe((resp3:Izdavanje[])=>{
              if(resp3){
                this.odobrenaIzdavanja=resp3;
                this.prodServ.getOdobrene().subscribe((resp4:Prodaja[])=>{
                  if(resp4){
                    this.odobreneProdaje=resp4;
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

  form:FormGroup;
  odobreniKorisnici:User[];
  neodobreneNekretnine:Nekretnina[];
  odobrenaIzdavanja:Izdavanje[];
  odobreneProdaje:Prodaja[];
  edit:boolean=false;

  username:string;
  ime:string
  prezime:string
  grad:string
  drzava:string
  

  poruka:string
  porukaUsername:string;
  porukaEmail:string;
  failed:boolean;
  updateUser(){
    let ime = this.user.ime;
    let prezime = this.user.prezime;
    let username = this.user.username;
    let grad = this.user.grad;
    let drzava = this.user.drzava;
    let email = this.user.email;
    let tip = this.user.tip;

    if(this.form.value.ime!=null&&!this.form.controls.ime.errors){
      ime=this.form.value.ime;
    }

    if(this.form.value.prezime!=null&&!this.form.controls.prezime.errors){
      prezime=this.form.value.prezime;
    }
    
    if(this.form.value.drzava!=null&&!this.form.controls.drzava.errors){
      drzava=this.form.value.drzava;
    }
    
    if(this.form.value.email!=null&&!this.form.controls.email.errors){
      email=this.form.value.email;
    }
    
    if(this.form.value.grad!=null&&!this.form.controls.grad.errors){
      grad=this.form.value.grad;
    }
    
    if(this.form.value.username!=null&&!this.form.controls.username.errors){
      username=this.form.value.username;
    }

    if(this.form.value.tip!=null&&!this.form.controls.tip.errors){
      tip=this.form.value.tip;
    }
    
    this.korServ.checkUsername(this.form.value.username).subscribe((resp:User[])=>{
      if(resp){
        this.porukaUsername="Korisnicko ime vec postoji";
      }
      else{
        this.korServ.checkEmail(this.form.value.email).subscribe((resp2:User[])=>{
          if(resp2){
            this.porukaEmail="Email ime vec postoji";
          }
          else{
            this.korServ.updateInfo(ime,prezime,this.user.username,email,grad,drzava,tip,username).subscribe(resp3=>{
              if(resp3){
                if(this.form.value.image!=null){
                  this.korServ.removePic(this.user.imagePath.substring(28)).subscribe(resp4=>{
                    if(resp4){
                      this.korServ.updatePic(username,this.form.value.image).subscribe(resp5=>{
                        if(resp5){
                          this.korServ.getUserS().subscribe((resp6:User[])=>{
                            if(resp6){
                              this.odobreniKorisnici=resp6;
                              this.reloadTimestamp();
                              this.edit=false;
                              this.porukaUsername="";
                              this.porukaEmail="";
                              this.form.value.image==null;
                              this.ime=null;
                              this.prezime=null;
                              this.grad=null;
                              this.drzava=null;
                              this.user.email = null;
                              this.user=null;
                              this.imageData=null;
                              this.form.reset();
                            }
                          })
                        }
                      });

                    }
                  })
                }
                else{
                  this.korServ.getUserS().subscribe((resp6:User[])=>{
                    if(resp6){
                      this.odobreniKorisnici=resp6;
                      this.reloadTimestamp();
                      this.edit=false;
                      this.porukaUsername="";
                      this.porukaEmail="";
                      this.form.value.image=null;
                      this.form.value.ime=null;
                      this.form.value.prezime=null;
                      this.form.value.email=null;
                      this.form.value.drzava=null;
                      this.form.value.grad=null;
                      this.imageData=null;
                      this.form.reset();
                      this.user=null;
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

  imageData:string;
  onFileSelect(event: Event){
    const file = (event.target as HTMLInputElement).files[0];
    this.form.patchValue({ image: file });
    const allowedMimeTypes = ["image/png", "image/jpeg", "image/jpg"];
    if (file && allowedMimeTypes.includes(file.type)) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imageData = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  user:User;
  editF(u:User){
    this.edit=true;
    this.user=u;
  }

  timeStamp:number;
  public getLinkPicture(user:User){
    if(this.timeStamp){
      return user.imagePath+'?'+this.timeStamp;
    }
    return user.imagePath;
  }

  public reloadTimestamp(){
    this.timeStamp=(new Date()).getTime();
  }

  removeUser(user:User){
    this.korServ.remove(user.username).subscribe(resp=>{
      if(resp){
        console.log(resp);
        this.korServ.getUserS().subscribe((resp:User[])=>{
          if(resp){
            this.odobreniKorisnici=resp;
          }
        })
      }
    })
  }
  odobri(user:User){
    this.korServ.odobri(user.username).subscribe(resp=>{
      if(resp){
        console.log(resp);
        this.korServ.getUserS().subscribe((resp:User[])=>{
          if(resp){
            this.odobreniKorisnici=resp;
          }
        })
      }
    })
  }

  odobriNek(nek:Nekretnina){
    this.nekServ.odobri(nek.IdNek).subscribe(resp=>{
      if(resp){
        this.nekServ.getNeodobrene().subscribe((resp2:Nekretnina[])=>{
          if(resp2){
            this.neodobreneNekretnine=resp2;
          }
        })
      }
    })
  }

  dodajKorisnika(){
    this.router.navigate(['register']);
  }

  dodajNekretninu(){
    this.router.navigate(['dodavanjeNekretnine']);
  }

  novProcenat:number;
  tipProcenta:string;
  dodajProcenat(){
    if(this.tipProcenta!=null&&typeof(this.novProcenat)==='number'&&this.novProcenat<1){
      this.korServ.addProcenat(this.novProcenat,this.tipProcenta).subscribe(resp=>{
        if(resp){
          console.log("ok");
          this.novProcenat=null;
          this.tipProcenta=null;
        }
      })
    }
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
