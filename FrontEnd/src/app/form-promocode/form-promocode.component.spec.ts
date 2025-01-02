import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormPromocodeComponent } from './form-promocode.component';

describe('FormPromocodeComponent', () => {
  let component: FormPromocodeComponent;
  let fixture: ComponentFixture<FormPromocodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormPromocodeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormPromocodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
