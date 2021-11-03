import { TestBed } from '@angular/core/testing';

import { ProdajaService } from './prodaja.service';

describe('ProdajaService', () => {
  let service: ProdajaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProdajaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
