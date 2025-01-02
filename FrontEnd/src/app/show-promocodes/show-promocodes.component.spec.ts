import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowPromocodesComponent } from './show-promocodes.component';

describe('ShowPromocodesComponent', () => {
  let component: ShowPromocodesComponent;
  let fixture: ComponentFixture<ShowPromocodesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowPromocodesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowPromocodesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
