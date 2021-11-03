import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DodavanjeNekretnineComponent } from './dodavanje-nekretnine.component';

describe('DodavanjeNekretnineComponent', () => {
  let component: DodavanjeNekretnineComponent;
  let fixture: ComponentFixture<DodavanjeNekretnineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DodavanjeNekretnineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DodavanjeNekretnineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
