import { ApplicationInitStatus, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { KorisnikService } from '../korisnik.service';
import User from '../models/User';

@Component({
  selector: 'app-prijava',
  templateUrl: './prijava.component.html',
  styleUrls: ['./prijava.component.css']
})
export class PrijavaComponent implements OnInit {

  constructor(private korServ:KorisnikService,
    private router:Router) { }

  ngOnInit(): void {
    this.form=new FormGroup({
      username:new FormControl(null,Validators.required),
      password:new FormControl(null, Validators.required)
    })
  
  }


  form:FormGroup;
  poruka:string;
  failed:boolean;

  prijava(){
    
    if(!this.form.controls.username.errors&&!this.form.controls.password.errors){
      this.korServ.prijava(this.form.value.username,this.form.value.password).subscribe((resp:User[])=>{
        if(resp.length==1){
          
          localStorage.setItem('user',JSON.stringify(resp[0]))
          localStorage.setItem('firstToken','first');
          this.router.navigate(['pocetna']);
          this.poruka="";
        }
        else{
          this.poruka="pogresno korisnicko ime ili lozinka";
        }
        this.failed=false;
      })
    }
    else{
      this.failed=true;
    }
  }


}
