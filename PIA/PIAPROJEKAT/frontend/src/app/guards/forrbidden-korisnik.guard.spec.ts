import { TestBed } from '@angular/core/testing';

import { ForrbiddenKorisnikGuard } from './forrbidden-korisnik.guard';

describe('ForrbiddenKorisnikGuard', () => {
  let guard: ForrbiddenKorisnikGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ForrbiddenKorisnikGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
