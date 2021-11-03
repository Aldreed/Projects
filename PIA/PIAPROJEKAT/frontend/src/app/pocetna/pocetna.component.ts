import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { from, fromEvent, range } from 'rxjs';
import Media from '../models/Media';
import Nekretnina from '../models/Nekretnina';
import NekretninaOmotac from '../models/NekretninaOmotac';
import User from '../models/User';
import { NekretninaService } from '../services/nekretnina.service';
import { Chart} from 'chart.js'
import { KorisnikService } from '../korisnik.service';
import { templateJitUrl } from '@angular/compiler';

@Component({
  selector: 'app-pocetna',
  templateUrl: './pocetna.component.html',
  styleUrls: ['./pocetna.component.css']
})
export class PocetnaComponent implements OnInit {
  
  constructor(private nekServ:NekretninaService,
    private korServ:KorisnikService,
    private router:Router) { 
    }
    
    
    ngOnInit(): void {
      if(localStorage.getItem('firstToken')){
          window.location.reload();
          localStorage.removeItem('firstToken');
          console.log("here");
        }
        if(localStorage.getItem('user')){
          this.user=JSON.parse(localStorage.getItem('user'));
        }
        this.form = new FormGroup({
          minCena:new FormControl(null),
          maxCena:new FormControl(null),
          grad:new FormControl(null)
        })
        
        this.nekServ.getFeaturedNekretina().subscribe((nekList:Nekretnina[])=>{
          this.featuredList=nekList
          console.log(nekList)
          nekList.forEach(element => {
            let nov = new NekretninaOmotac()
            nov.media=[];
            nov.nekretnina=element;
            this.nekServ.getPictures(element.IdNek).subscribe((resp:Media[])=>{
              if(resp.length>0){
                nov.media=resp;
              }
              else{
                let newMedia = new Media();
                newMedia.mediaPath="http://localhost:4000/preparedImages/basicHouse.png";
                nov.media.push(newMedia);
              }
              
              let num =Math.floor(Math.random() * (nov.media.length));//random
              nov.pickedMedia=nov.media[num].mediaPath;//random media
              this.featuredOmotac.push(nov);
              this.showSlides(1);
            })
            
          });
          this.chartFunction();
          this.getKuceChart();
        })
        this.formPrijava=new FormGroup({
          username:new FormControl(null,Validators.required),
          password:new FormControl(null, Validators.required)
        })
        
      }
      
      user:User;
      form:FormGroup;
      
      featuredList:Nekretnina[]
      searchList:Nekretnina[]
      featuredOmotac:NekretninaOmotac[]=[]
      searchedOmotac:NekretninaOmotac[]=[];
      
  choose(nek:Nekretnina){
      if(this.user!=null&&this.user.tip!="admin"&&this.user.tip!="agent"){
          localStorage.setItem('izabranaNekretnina',JSON.stringify(nek));
          this.router.navigate(['stranicaNekretnine']);
        }
  }

  pretraga(){

    this.searchedOmotac=[];
    if(!this.form.value.minCena&&!this.form.value.maxCena&&this.form.value.grad){
      this.nekServ.pretragaPoGradu(this.form.value.grad).subscribe((nek:Nekretnina[])=>{
        if(nek){
          this.searchList=nek;
          this.getSeachedPictures();
        }
      })
    }
    else if(this.form.value.minCena&&this.form.value.maxCena&&!this.form.value.grad){
      this.nekServ.pretragaPoCeni(this.form.value.minCena,this.form.value.maxCena).subscribe((nek:Nekretnina[])=>{
        if(nek){
          this.searchList=nek;
          this.getSeachedPictures();
        }
      })
    }
    else if(this.form.value.minCena&&!this.form.value.maxCena&&!this.form.value.grad){
      this.nekServ.pretragaIznadCene(this.form.value.minCena).subscribe((nek:Nekretnina[])=>{
        if(nek){
          this.searchList=nek;
          this.getSeachedPictures();
        }
      })
    }
    else if(!this.form.value.minCena&&this.form.value.maxCena&&!this.form.value.grad){
      this.nekServ.pretragaIspodCene(this.form.value.maxCena).subscribe((nek:Nekretnina[])=>{
        if(nek){
          this.searchList=nek;
          this.getSeachedPictures();
        }
      })
    }
    else if(this.form.value.minCena&&!this.form.value.maxCena&&this.form.value.grad){
      this.nekServ.pretragaIznadCeneIGradu(this.form.value.minCena,this.form.value.grad).subscribe((nek:Nekretnina[])=>{
        if(nek){
          this.searchList=nek;
          this.getSeachedPictures();
        }
      })
    }
    else if(!this.form.value.minCena&&this.form.value.maxCena&&this.form.value.grad){
      this.nekServ.pretragaIspodCeneIGradu(this.form.value.maxCena,this.form.value.grad).subscribe((nek:Nekretnina[])=>{
        if(nek){
          this.searchList=nek;
          this.getSeachedPictures();
        }
      })
    }
    else if(this.form.value.minCena&&this.form.value.maxCena&&this.form.value.grad){
      this.nekServ.pretragaPoCeniIGradu(this.form.value.minCena,this.form.value.maxCena,this.form.value.grad).subscribe((nek:Nekretnina[])=>{
        if(nek){
          this.searchList=nek;
          this.getSeachedPictures();
        }
      })
    }
    else{

    }

     this.searchedOmotac=this.searchedOmotac;


  }


  private getSeachedPictures(){
    this.searchList.forEach(element => {
      this.nekServ.getPictures(element.IdNek).subscribe((resp:Media[])=>{
        let nov = new NekretninaOmotac();
        nov.nekretnina=element;
        if(resp.length>0){
          nov.media=resp;
          let num =Math.floor(Math.random() * (nov.media.length));
          console.log(num);
          nov.pickedMedia=nov.media[num].mediaPath;
        }
        else{
          nov.pickedMedia="http://localhost:4000/images/basicHouse.png";
        }
        this.searchedOmotac.push(nov);
      })
    });

  
  }


  slideIndex:number = 0;

  // Next/previous controls
 plusSlides(n) {
  this.showSlides(this.slideIndex += n);
}

// Thumbnail image controls
currentSlide(n) {
  this.showSlides(this.slideIndex = n);
}

showSlides(n) {
  var i;
  var slides:any[] = document.getElementsByClassName("mySlides") as unknown as HTMLDivElement[];
  var dots:any[] = document.getElementsByClassName("slideImage") as unknown as HTMLImageElement[];
  var captionText = document.getElementById("caption");
  if (n > slides.length) {this.slideIndex = 1}
  if (n < 1) {this.slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  // for (i = 0; i < dots.length; i++) {
  //   dots[i].className = dots[i].className.replace(" active", "");
  // }
  slides[this.slideIndex-1].style.display = "block";
  //dots[this.slideIndex-1].className += " active";
   captionText.innerHTML = dots[this.slideIndex-1].alt;
}

getKuceChart(){
  this.stepActive=false;
  let names:string[] = ['Izdavanje','Prodaja'];
  let values:number[] = [0,0];
  this.nekServ.getKucaIzd().subscribe((resp:Nekretnina[])=>{
    if(resp){
      values[0]=resp.length;
      this.nekServ.getKucaProd().subscribe((resp2:Nekretnina[])=>{
        if(resp2){
          values[1]=resp2.length;
          this.chartUpdate(names,values,"Br. Kuca")
        }
      })
    }
  })

}

getStanChart(){
  this.stepActive=false;
  let names:string[] = ['Izdavanje','Prodaja'];
  let values:number[] = [0,0];
  this.nekServ.getStanIzd().subscribe((resp:Nekretnina[])=>{
    if(resp){
      values[0]=resp.length;
      this.nekServ.getStanProd().subscribe((resp2:Nekretnina[])=>{
        if(resp2){
          values[1]=resp2.length;
          console.log(values);
          this.chartUpdate(names,values,"Br. Stanova")
        }
      })
    }
  })

}

getCityChart(){
  this.stepActive=false;
  let names:string[] = [];
  let values:number[] = [];
  this.nekServ.getAllNekretina().subscribe((resp:Nekretnina[])=>{
    if(resp){
      let map = new Map();

      for (let i = 0; i < resp.length; i++) {
        const element = resp[i];
        console.log(element.grad);
        if(map.has(element.grad)){
          let temp:number = map.get(element.grad);
          
          temp=temp+1;
          map.set(element.grad,temp);
        }
        else{
          console.log("here");
          map.set(element.grad,1);
        }

      }

      resp.forEach(element => {

        
      });
      for (let key of map.keys()) {
        names.push(key);                   
      }
      
      for (let key of map.values()) {
        values.push(key);                   
      }
      this.chartUpdate(names,values,"Br. nekretina")
    }
  })

}

step = 100;
stepActive:boolean=false;
getPriceChart(){
  this.stepActive=true;
  if(typeof(this.step)!=='number'||this.step<=0||this.step==null){
    this.step=100;
  }
  let names:string[] = [];
  let values:number[] = [];
  this.nekServ.getAllNekretina().subscribe((resp:Nekretnina[])=>{
    if(resp){
      let temp: Nekretnina[]=resp.sort(this.sorterHelper);
      let prev:number=0
      let next:number=this.step;
      let step:number=this.step;
      let min = temp[0].cena;
      let max = temp[temp.length-1].cena;
      let map = new Map();
      for (let i = 0; i < temp.length; i++) {
        const element = temp[i];

        let key:string = "["+prev+"-"+next+"]";

        // if(next>=max){
        //   key="[>"+max+"]"
        // }

        if(element.cena>=prev&&element.cena<next){
          if(map.has(key)){
            let temp:number = map.get(key);
            temp=temp+1;
            map.set(key,temp);
          }
          else{
            map.set(key,1);
          }
        }
        else if(element.cena>=next){
          while(element.cena>=next){
            prev=next;
            next=next+step;  
          }
          key = "["+prev+"-"+next+"]";
          // if(prev>=max){
          //   key="[>"+max+"]"
          // }
          if(map.has(key)){
            let temp:number = map.get(element.grad);
            temp=temp+1;
            map.set(key,temp);
          }
          else{
            map.set(key,1);
          }
        } 
      }
      for (let key of map.keys()) {
        names.push(key);                   
      }
      
      for (let key of map.values()) {
        values.push(key);                   
      }
      
      this.chartUpdate(names,values,"Br.nekretnina");
    }
  })
}

sorterHelper(n1:Nekretnina,n2:Nekretnina){
  if(n1.cena>n2.cena){
    return 1;
  }
  else if(n1.cena<n2.cena){
    return -1
  }
  else{
    return 0;
  }
}


chart:Chart;

chartUpdate(names:string[],values:number[],name){
  // this.chart.data.labels.forEach(element => {
  //   console.log(this.chart.data.labels);
  //   this.chart.data.labels.pop();
  //   console.log(this.chart.data.labels);
  //   console.log("pop");
  //   this.chart.data.datasets.forEach((dataset) => {
  //     dataset.data.pop();
  //   });
  // });

  this.chart.data.labels=[];
  this.chart.data.datasets.forEach((dataset) => {
        dataset.data=[];
      });
  
  names.forEach(element => {
    this.chart.data.labels.push(element);
    this.chart.data.datasets.forEach((dataset) => {
      dataset.data.push(values[names.indexOf(element)]);
    });
  });
  
    
      this.chart.update();
}


chartFunction(){
  
var ctx = (document.getElementById("myChart")as HTMLCanvasElement).getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: [],
        datasets: [{
            label: "Nekretnine",
            data: [],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {

      scales: {
        yAxes: [{
          display: true,
          ticks: {
            beginAtZero: true,
            min: 0,
            stepSize: 1,
            fontSize: 16
          }
        }],
        xAxes: [{
          ticks: {
            fontSize: 16
          }
        }]
      }
    
      
        // scales: {
          
        //     // yAxes: {
        //     //     beginAtZero: true
                
        //     // }
        // }
    }
});
this.chart=myChart;

}


//prijava


formPrijava:FormGroup;
poruka:string;
failed:boolean;

prijava(){
  
  if(!this.formPrijava.controls.username.errors&&!this.formPrijava.controls.password.errors){
    this.korServ.prijava(this.formPrijava.value.username,this.formPrijava.value.password).subscribe((resp:User[])=>{
      if(resp.length==1){
        
        localStorage.setItem('user',JSON.stringify(resp[0]))
        localStorage.setItem('firstToken','first');
        this.poruka="";
        // window.location.reload();
        this.router.navigate(["pocetna"]);

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
