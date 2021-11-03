import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ActivnePorukeComponent } from './activne-poruke/activne-poruke.component';
import { AdminComponent } from './admin/admin.component';
import { AgentWindowComponent } from './agent-window/agent-window.component';
import { AppComponent } from './app.component';
import { ArhivaComponent } from './arhiva/arhiva.component';
import { DodavanjeNekretnineComponent } from './dodavanje-nekretnine/dodavanje-nekretnine.component';
import { EditNekComponent } from './edit-nek/edit-nek.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { AdminGuard } from './guards/admin.guard';
import { AgentGuard } from './guards/agent.guard';
import { ForrbiddenAdminGuard } from './guards/forrbidden-admin.guard';
import { ForrbiddenAgentGuard } from './guards/forrbidden-agent.guard';
import { ForrbiddenGostGuard } from './guards/forrbidden-gost.guard';
import { ForrbiddenKorisnikGuard } from './guards/forrbidden-korisnik.guard';
import { GostGuard } from './guards/gost.guard';
import { KorisnikGuard } from './guards/korisnik.guard';
import { KonverzacijaComponent } from './konverzacija/konverzacija.component';
import { PocetnaComponent } from './pocetna/pocetna.component';
import { PrijavaComponent } from './prijava/prijava.component';
import { RegisterComponent } from './register/register.component';
import { StranicaNekretnineComponent } from './stranica-nekretnine/stranica-nekretnine.component';

const routes: Routes = [
  {path:"register",component:RegisterComponent, canActivate:[ForrbiddenAgentGuard,ForrbiddenKorisnikGuard]},
  {path:"dodavanjeNekretnine",component:DodavanjeNekretnineComponent, canActivate:[ForrbiddenGostGuard] },
  {path:"stranicaNekretnine",component:StranicaNekretnineComponent, canActivate:[KorisnikGuard]},
  {path:"prijava",component:PrijavaComponent, canActivate:[GostGuard]},
  {path:"aktivnePoruke/konverzacija",component:KonverzacijaComponent,canActivate:[ForrbiddenGostGuard,ForrbiddenAdminGuard]},
  {path:"aktivnePoruke",component:ActivnePorukeComponent,canActivate:[ForrbiddenGostGuard,ForrbiddenAdminGuard]},
  {path:"aktivnePoruke/arhiva",component:ArhivaComponent,canActivate:[ForrbiddenGostGuard,ForrbiddenAdminGuard]},
  {path:"agent",component:AgentWindowComponent, canActivate:[AgentGuard]},
  {path:"admin",component:AdminComponent , canActivate:[AdminGuard]},
  {path:"pocetna",component:PocetnaComponent},
  {path:"editUser",component:EditUserComponent, canActivate:[KorisnikGuard]},
  {path:"editUser/editNek",component:EditNekComponent, canActivate:[KorisnikGuard]},
  {path:"",component:PocetnaComponent},
  {path:"**",component:PocetnaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
