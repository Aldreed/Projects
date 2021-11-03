import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { KorisnikService } from '../korisnik.service';
import User from '../models/User';
import { containsValidator } from '../Validators/Contains';
import { containsNotValidator } from '../Validators/DosntContain';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  constructor(private korServ:KorisnikService,
    private router:Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      ime: new FormControl(null,Validators.required),
      prezime: new FormControl(null,Validators.required),
      email: new FormControl(null,[Validators.pattern("[^ @]*@[^ @]*"),Validators.required]),
      grad: new FormControl(null,Validators.required),
      drzava: new FormControl(null,Validators.required),
      image: new FormControl(null)
    })
  
    //Promena Lozinke
    
    this.passwordForm = new FormGroup({
      staraLoz:new FormControl(null,Validators.required),
      novaLoz:new FormControl(null,[
        containsValidator(/[a-z]{1,}/),
        containsValidator(/[A-Z]{1,}/),
        containsValidator(/\d{1,}/),
        containsValidator(/[\!\@\#\$\%\^\&\*\(\)\-\_\\\<\>\,\.\?\/~\`\:\;\"\'\{\}\[\]\|]/),
        containsNotValidator(/([a-zA-Z\d\!\@\#\$\%\^\&\*\(\)\-\_\\\<\>\,\.\?\/~\`\:\;\"\'\{\}\[\]\|])\1\1\1/),
        Validators.minLength(8),
        Validators.maxLength(24),
        Validators.required
      ]),
      potvrdaLoz:new FormControl(null,Validators.required),
    })

      this.user=JSON.parse(localStorage.getItem('user'));
      

  }



  edit:boolean=false;
  form:FormGroup;
  user:User;

  ime:string;
  prezime:string;
  grad:string;
  drzava:string;

  porukaEmail:string;
  
  failed:boolean;
  poruka:string
  updateUser(){
    let ime = this.user.ime;
    let prezime = this.user.prezime;
    
    let grad = this.user.grad;
    let drzava = this.user.drzava;
    let email = this.user.email;
   

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
    
    

    
        this.korServ.checkEmail(this.form.value.email).subscribe((resp2:User[])=>{
          if(resp2){
            this.porukaEmail="Email ime vec postoji";
          }
          else{
            this.korServ.updateInfo(ime,prezime,this.user.username,email,grad,drzava,this.user.tip,this.user.username).subscribe(resp3=>{
              if(resp3){
                if(this.form.value.image!=null){
                  this.korServ.removePic(this.user.imagePath.substring(28)).subscribe(resp4=>{
                    if(resp4){
                      this.korServ.updatePic(this.user.username,this.form.value.image).subscribe(resp5=>{
                        if(resp5){
                          this.korServ.prijava(this.user.username,this.user.lozinka).subscribe((resp6:User[])=>{
                            if(resp6.length==1){
                              this.user=resp6[0];
                              localStorage.setItem('user',JSON.stringify(resp6[0]))
                              this.reloadTimestamp();
                              this.edit=false;
                              this.porukaEmail="";
                              this.form.value.image=null;
                              this.form.value.ime=null;
                              this.form.value.prezime=null;
                              this.form.value.email=null;
                              this.form.value.drzava=null;
                              this.form.value.grad=null;
                              this.form.reset();
                            }
                          })
                        }
                      });

                    }
                  })
                }
                else{
                  this.korServ.prijava(this.user.username,this.user.lozinka).subscribe((resp6:User[])=>{
                    if(resp6.length==1){
                      this.user=resp6[0];
                      localStorage.setItem('user',JSON.stringify(resp6[0]))
                      this.reloadTimestamp();
                      this.edit=false;
                      this.porukaEmail="";
                      this.form.value.image=null;
                      this.form.value.ime=null;
                      this.form.value.prezime=null;
                      this.form.value.email=null;
                      this.form.value.drzava=null;
                      this.form.value.grad=null;
                      this.form.reset();
                    }
                  })
                }
              }
            })
          }
      })
  }


  timeStamp:number;
  public getLinkPicture(){
    if(this.timeStamp){
      return this.user.imagePath+'?'+this.timeStamp;
    }
    return this.user.imagePath;
  }

  public reloadTimestamp(){
    this.timeStamp=(new Date()).getTime();
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


  //lozinka
  passwordForm:FormGroup;
  nepoklapanjeLozinki:string;
  pogresnaLoz:string;
  badPass:boolean;
  promeni(){

    if(!this.passwordForm.controls.staraLoz.errors&&!this.passwordForm.controls.novaLoz.errors
      &&!this.passwordForm.controls.potvrdaLoz.errors&&this.passwordForm.value.novaLoz==this.passwordForm.value.potvrdaLoz&&
      this.user.lozinka==this.passwordForm.value.staraLoz){
        this.badPass=false;
        

        this.korServ.promeniLoz(this.user.username,this.passwordForm.value.novaLoz).subscribe(res=>{
          if(res){
            console.log(res);
            localStorage.clear();
            localStorage.setItem("firstToken","first")
            this.router.navigate(['pocetna']);
          }
        })

        
      }
      else if(this.user.lozinka!=this.passwordForm.value.staraLoz){
        
        this.pogresnaLoz="pogresno uneta lozinka"
        this.badPass=true;
      }
      else if(this.passwordForm.value.novaLoz!=this.passwordForm.value.potvrdaLoz){
        this.nepoklapanjeLozinki="nova lozinka i potvrda moraju da se poklapaju"
        this.badPass=false;
      }
      
  }
}
