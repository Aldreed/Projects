import { TestBed } from '@angular/core/testing';

import { GostGuard } from './gost.guard';

describe('GostGuard', () => {
  let guard: GostGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(GostGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
