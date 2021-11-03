import { TestBed } from '@angular/core/testing';

import { ForrbiddenGostGuard } from './forrbidden-gost.guard';

describe('ForrbiddenGostGuard', () => {
  let guard: ForrbiddenGostGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ForrbiddenGostGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
