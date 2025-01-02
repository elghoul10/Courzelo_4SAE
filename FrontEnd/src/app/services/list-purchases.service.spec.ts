import { TestBed } from '@angular/core/testing';

import { ListPurchasesService } from './list-purchases.service';

describe('ListPurchasesService', () => {
  let service: ListPurchasesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListPurchasesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
