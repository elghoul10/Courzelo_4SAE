import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-form-promocode',
  templateUrl: './form-promocode.component.html',
  styleUrls: ['./form-promocode.component.scss']
})
export class FormPromocodeComponent implements OnInit {

formPromocode!:FormGroup;
  constructor(private acr : ActivatedRoute){

  }
  
  ngOnInit(): void {
    this.formPromocode= new FormGroup({
      promoCode :new FormControl('',Validators.required),
      Discount :new FormControl('',Validators.required)
  
    })
  }

  add(){

  }
}
