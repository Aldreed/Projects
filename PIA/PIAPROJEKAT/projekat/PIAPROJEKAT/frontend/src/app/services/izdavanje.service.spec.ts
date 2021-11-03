import { TestBed } from '@angular/core/testing';

import { IzdavanjeService } from './izdavanje.service';

describe('IzdavanjeService', () => {
  let service: IzdavanjeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IzdavanjeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
