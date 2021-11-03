import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { KorisnikService } from '../korisnik.service';
import User from '../models/User';
import { containsValidator } from '../Validators/Contains';
import { containsNotValidator } from '../Validators/DosntContain';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private userServ :KorisnikService,
    private router: Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      ime: new FormControl(null,Validators.required),
      prezime: new FormControl(null,Validators.required),
      username: new FormControl(null,Validators.required),
      lozinka: new FormControl(null,[
        containsValidator(/[a-z]{1,}/),
        containsValidator(/[A-Z]{1,}/),
        containsValidator(/\d{1,}/),
        containsValidator(/[\!\@\#\$\%\^\&\*\(\)\-\_\\\<\>\,\.\?\/~\`\:\;\"\'\{\}\[\]\|]/),
        containsNotValidator(/([a-zA-Z\d\!\@\#\$\%\^\&\*\(\)\-\_\\\<\>\,\.\?\/~\`\:\;\"\'\{\}\[\]\|])\1\1\1/),
        Validators.minLength(8),
        Validators.maxLength(24),
        Validators.required
      ]),
      email: new FormControl(null,[Validators.pattern("[^ @]*@[^ @]*"),Validators.required]),
      grad: new FormControl(null,Validators.required),
      drzava: new FormControl(null,Validators.required),
      tip: new FormControl(null),
      image: new FormControl(null),
    })

    
    this.user=JSON.parse(localStorage.getItem('user'));
  }


  form:FormGroup;
  imageData:string;
  poruka:String;
  failed:Boolean;
  porukaBackendUsername:String;
  porukaBackendEmail:String;
  fileFormat:string;
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
      this.fileFormat="";
    }
    else{
      this.form.patchValue({ image: null });
      this.fileFormat="Pogresan tip fajla"
    }
  }

  onSubmit(){

    
    if(!this.form.value.ime||!this.form.value.prezime||!this.form.value.username||!this.form.value.lozinka||!this.form.value.grad||!this.form.value.drzava){
      this.poruka='ovo polje je obavezno';
      this.failed=true;
    }
    else if(this.form.controls.ime.errors||this.form.controls.prezime.errors||this.form.controls.username.errors||this.form.controls.lozinka.errors||this.form.controls.grad.errors||this.form.controls.drzava.errors){
      this.poruka='ovo polje je obavezno';
      this.failed=true;
    }
    else if(this.form.controls.lozinka.errors){
      this.poruka='ovo polje je obavezno';
      this.failed=true;

    }
    else if(this.form.controls.email.errors){
      this.poruka='pogresno unet email';
      this.failed=true;

    }
    else if(this.fileFormat){

    }
    else{
          this.porukaBackendEmail=null;
          this.porukaBackendUsername=null;

      this.userServ.checkEmail(this.form.value.email).subscribe((res)=>{
        if(res){
          this.porukaBackendEmail ='Email postoji';
          this.failed=true;
        }
        else{
          this.userServ.checkUsername(this.form.value.username).subscribe((res)=>{
            if(res){
              console.log(this.form.controls.ime.errors);
              this.porukaBackendUsername ='Korisnicko ime postoji';
              this.failed=true;
            }
            else{
              this.failed=false;
              let tip = 'korisnik';
              if(this.form.value.tip!=null){
                tip=this.form.value.tip
              }

              if(this.form.value.image==null){
                this.userServ.addUserT(this.form.value.ime,
                  this.form.value.prezime,
                  this.form.value.username,
                  this.form.value.lozinka,
                  this.form.value.email,
                  this.form.value.grad,
                  this.form.value.drzava,
                  tip).subscribe((res)=>{
                    console.log("ok");
                    this.router.navigate(["pocetna"]);
                  })
              }
              else{
                this.userServ.addUser(this.form.value.ime,
                  this.form.value.prezime,
                  this.form.value.username,
                  this.form.value.lozinka,
                  this.form.value.email,
                  this.form.value.grad,
                  this.form.value.drzava,
                  tip,
                  this.form.value.image).subscribe((res)=>{
                    console.log("ok");
                    this.router.navigate(["pocetna"]);
                  })
               }
            }
          })
        }
      })
      }  
  }


  userArr:User[];

  getAll(){
    this.userServ.getUserS().subscribe((res:User[])=>{
      this.userArr=res;
      console.log('hi');
      console.log(this.userArr);
    })
  }


  //Lozinka

  passwordForm:FormGroup;
  nepoklapanjeLozinki:string;
  pogresnaLoz:string;
  badPass:boolean;
  user:User;
  promeni(){

    if(!this.passwordForm.controls.staraLoz.errors&&!this.passwordForm.controls.novaLoz.errors
      &&!this.passwordForm.controls.potvrdaLoz.errors&&this.passwordForm.value.novaLoz==this.passwordForm.value.potvrdaLoz&&
      this.user.lozinka==this.passwordForm.value.staraLoz){
        this.badPass=false;
        

        this.userServ.promeniLoz(this.user.username,this.passwordForm.value.novaLoz).subscribe(res=>{
          if(res){
            console.log(res);
            localStorage.clear();
            this.router.navigate(['']);
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

  nazad(){
    if(this.user.tip=="admin"){
      this.router.navigate(['admin']);
    }
  }
}
