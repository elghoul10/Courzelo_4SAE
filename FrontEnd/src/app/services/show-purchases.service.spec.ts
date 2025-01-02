import { TestBed } from '@angular/core/testing';

import { ShowPurchasesService } from './show-purchases.service';

describe('ShowPurchasesService', () => {
  let service: ShowPurchasesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShowPurchasesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
