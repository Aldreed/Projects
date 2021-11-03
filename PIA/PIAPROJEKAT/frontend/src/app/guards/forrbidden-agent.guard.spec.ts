import { TestBed } from '@angular/core/testing';

import { ForrbiddenAgentGuard } from './forrbidden-agent.guard';

describe('ForrbiddenAgentGuard', () => {
  let guard: ForrbiddenAgentGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ForrbiddenAgentGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
