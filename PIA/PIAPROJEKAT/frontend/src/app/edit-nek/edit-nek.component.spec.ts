import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditNekComponent } from './edit-nek.component';

describe('EditNekComponent', () => {
  let component: EditNekComponent;
  let fixture: ComponentFixture<EditNekComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditNekComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditNekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
