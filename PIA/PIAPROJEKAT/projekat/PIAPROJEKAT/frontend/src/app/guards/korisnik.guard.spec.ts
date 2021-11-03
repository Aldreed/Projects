import { TestBed } from '@angular/core/testing';

import { KorisnikGuard } from './korisnik.guard';

describe('KorisnikGuard', () => {
  let guard: KorisnikGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(KorisnikGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
