import { Component, Inject, Input } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BasketService } from 'app/services/basket.service';

@Component({
  selector: 'app-popup',
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.scss']
})
export class PopupComponent {
  imageUrl: string = '/assets/images/cart1.jpg';
  imageUrl1: string = '/assets/images/OdxcctP.jpg';
  imageUrl2: string = '/assets/images/WIAP9Ku.jpg';
  constructor(private BasketService: BasketService,@Inject(MAT_DIALOG_DATA) public data: any) {
    this.id = data.id;

   }
  @Input() id: any;

 
  confirmPurcheses(idb: any): void{
    
    this.BasketService.confirmPurcheses(idb).subscribe({
      next: () => {
        window.location.reload()
      },
      error: (error) => {
        console.error('An error occurred while deleting item:', error);
      }
    });
  }

}
