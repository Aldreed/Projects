import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentWindowComponent } from './agent-window.component';

describe('AgentWindowComponent', () => {
  let component: AgentWindowComponent;
  let fixture: ComponentFixture<AgentWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentWindowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
