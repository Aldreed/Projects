import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivnePorukeComponent } from './activne-poruke.component';

describe('ActivnePorukeComponent', () => {
  let component: ActivnePorukeComponent;
  let fixture: ComponentFixture<ActivnePorukeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActivnePorukeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivnePorukeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
