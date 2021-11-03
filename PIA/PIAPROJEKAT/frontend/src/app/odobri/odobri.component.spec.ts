import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OdobriComponent } from './odobri.component';

describe('OdobriComponent', () => {
  let component: OdobriComponent;
  let fixture: ComponentFixture<OdobriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OdobriComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OdobriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
