import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StranicaNekretnineComponent } from './stranica-nekretnine.component';

describe('StranicaNekretnineComponent', () => {
  let component: StranicaNekretnineComponent;
  let fixture: ComponentFixture<StranicaNekretnineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StranicaNekretnineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StranicaNekretnineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
