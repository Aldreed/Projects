import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import User from './models/User';

@Injectable({
  providedIn: 'root'
})
export class KorisnikService {

  constructor(private http :HttpClient) { }

   uri='http://localhost:4000';

  
   prijava(username,password){
    const data ={
      username:username,
      lozinka:password
    }

    return this.http.post(`${this.uri}/user/prijava`,data);
  }

  promeniLoz(username,password){
    const data ={
      username:username,
      lozinka:password
    }

    return this.http.post(`${this.uri}/user/promeniLoz`,data);
  }

   addUser(ime,prezime,username,password,email,grad,drzava,tip,image){
    const dat ={
      ime:ime,
      prezime:prezime,
      username:username,
      lozinka:password,
      email:email,
      grad:grad,
      drzava:drzava,
      tip,
      image:image

    }

    const data = new FormData();
    data.append('ime',ime);
    data.append('prezime',prezime);
    data.append('username',username);
    data.append('lozinka',password);
    data.append('email',email);
    data.append('grad',grad);
    data.append('drzava',drzava);
    data.append('image',image,username);
    data.append('tip',tip);
    data.append('odobren','false');

    return this.http.post(`${this.uri}/user/add`,data);
  
  }


  addUserT(ime,prezime,username,password,email,grad,drzava,tip){
    const dat ={
      ime:ime,
      prezime:prezime,
      username:username,
      lozinka:password,
      email:email,
      grad:grad,
      drzava:drzava,
      tip:tip,
      odobren:false
    }

    const data = new FormData();
    data.append('ime',ime);
    data.append('prezime',prezime);
    data.append('username',username);
    data.append('lozinka',password);
    data.append('email',email);
    data.append('grad',grad);
    data.append('drzava',drzava);
    data.append('tip',tip);
    data.append('odobren','false');

    return this.http.post(`${this.uri}/user/addT`,dat);
  
  }

  getUserS(){
    return this.http.get(`${this.uri}/user/getAll`);
  }

  checkUsername(username){
    const data ={
      username:username
    }
    return this.http.post(`${this.uri}/user/checkUsername`,data);

  }

  checkEmail(email){
    
    const data ={
      email:email
    }
    return this.http.post(`${this.uri}/user/checkEmail`,data);
  }


  odobri(username){
    const data={
      username:username
    }
    return this.http.post(`${this.uri}/user/odobri`,data);
  }

  remove(username){
    const data={
      username:username
    }
    return this.http.post(`${this.uri}/user/remove`,data);
  }


  updateInfo(ime,prezime,username,email,grad,drzava,tip,newUsername){

    const data = new FormData();
    data.append('ime',ime);
    data.append('prezime',prezime);
    data.append('username',username);
    data.append('email',email);
    data.append('grad',grad);
    data.append('drzava',drzava);
    data.append('tip',tip);
    data.append('newUsername',newUsername);

    const dat ={
      ime:ime,
      prezime:prezime,
      username:username,
      email:email,
      grad:grad,
      drzava:drzava,
      tip:tip,
      newUsername:newUsername
    }

    return this.http.post(`${this.uri}/user/updateInfo`,dat);
  
  }

  updatePic(username,image){
    const data = new FormData();
    data.append('username',username);
    data.append('image',image,username);

    return this.http.post(`${this.uri}/user/updatePic`,data);
  }

  removePic(oldImage){
    const data ={
      oldImage:oldImage
    }
    return this.http.post(`${this.uri}/user/removePic`,data)
  }

  addProcenat(procenat,tip){
    const data={
      procenat:procenat,
      tip:tip
    }
    return this.http.post(`${this.uri}/procenat/dodaj`,data)
  }

}
