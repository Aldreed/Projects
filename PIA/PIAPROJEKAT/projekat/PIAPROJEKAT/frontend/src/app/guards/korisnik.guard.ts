import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import User from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class KorisnikGuard implements CanActivate {
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(localStorage.getItem('user')){
        let user:User = JSON.parse(localStorage.getItem('user'));
        if(user.tip=="korisnik"){
          return true;
        }
        else{
          
          return false;
        }
      }
      else{
        
        return false
      }
  }
  
}
