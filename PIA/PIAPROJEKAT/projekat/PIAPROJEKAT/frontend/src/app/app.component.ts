import { Component, OnInit } from '@angular/core';
import { Router, ROUTER_INITIALIZER } from '@angular/router';
import User from './models/User';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'app';
  constructor(private router:Router) {}
  
  ngOnInit(): void {
    console.log('hi');
    if(localStorage.getItem('user')){
      this.render=true;
      this.user = JSON.parse(localStorage.getItem('user'));
    }
  }
  user:User;
  render:boolean=false;

  logout(){
    localStorage.clear()
    localStorage.setItem('firstToken','first');
    this.router.navigate(['']);
  }



}
