import { TestBed } from '@angular/core/testing';

import { ForrbiddenAdminGuard } from './forrbidden-admin.guard';

describe('ForrbiddenAdminGuard', () => {
  let guard: ForrbiddenAdminGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ForrbiddenAdminGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
