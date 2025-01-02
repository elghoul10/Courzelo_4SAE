import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { BasketService } from 'app/services/basket.service';
import { ListPurchasesService } from 'app/services/list-purchases.service';
import jsPDF from 'jspdf';

@Component({
  selector: 'app-list-purchases',
  templateUrl: './list-purchases.component.html',
  styleUrls: ['./list-purchases.component.scss']
})
export class ListPurchasesComponent  implements OnInit{
  query:any = '';
  date:Date;
  listPurchases : any;
  constructor(private purchasesService: ListPurchasesService,private BasketService: BasketService,private cdr: ChangeDetectorRef) { }
  
  ngOnInit(): void {
    this.purchasesService.ShowAll().subscribe(data => {
      this.listPurchases = data;
      console.log(this.listPurchases);

    });

  }
  onSubmit() {
    console.log("Form submitted with query:", this.query);
    console.log("Form date:", this.date);

    this.BasketService.Search(this.query,this.date).subscribe(data => {
      this.listPurchases = data;
      console.log(this.listPurchases);
    });
  }
  
  
}
