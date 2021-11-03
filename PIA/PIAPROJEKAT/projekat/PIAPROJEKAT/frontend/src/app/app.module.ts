import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';

import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import { DodavanjeNekretnineComponent } from './dodavanje-nekretnine/dodavanje-nekretnine.component';
import { PocetnaComponent } from './pocetna/pocetna.component';
import { StranicaNekretnineComponent } from './stranica-nekretnine/stranica-nekretnine.component';

import {FormsModule} from '@angular/forms';
import { PrijavaComponent } from './prijava/prijava.component';
import { KonverzacijaComponent } from './konverzacija/konverzacija.component';
import { ArhivaComponent } from './arhiva/arhiva.component';
import { ActivnePorukeComponent } from './activne-poruke/activne-poruke.component';
import { OdobriComponent } from './odobri/odobri.component';
import { AgentWindowComponent } from './agent-window/agent-window.component';
import { AdminComponent } from './admin/admin.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { EditNekComponent } from './edit-nek/edit-nek.component'
@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    DodavanjeNekretnineComponent,
    PocetnaComponent,
    StranicaNekretnineComponent,
    PrijavaComponent,
    KonverzacijaComponent,
    ArhivaComponent,
    ActivnePorukeComponent,
    OdobriComponent,
    AgentWindowComponent,
    AdminComponent,
    EditUserComponent,
    EditNekComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
