import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PopupComponent } from 'app/popup/popup.component';
import { PopupService } from 'app/popup/popup.service';
import { BasketService } from 'app/services/basket.service';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss'],
})
export class BasketComponent implements OnInit {
  backendData: any;
  couponValue: string = '';
  msg: any = '';
  id: any;
  imageUrl: string = '/assets/images/cart1.jpg';

  placeholder: String = 'Enter promotional code';
  constructor(
    private BasketService: BasketService,
    private dialog: MatDialog
  ) {}
  openPopup() {
    this.id = this.backendData.id;
    this.dialog.open(PopupComponent, {
      data: { id: this.id }, // Pass data to the PopupComponent
    });
  }
  ngOnInit(): void {
    this.BasketService.ShowBasket(1).subscribe((data) => {
      this.backendData = data;
    });
  }

  DeleteItem(idc: any, idb: any): void {
    this.BasketService.deleteItemFromBasket(idc, idb).subscribe({
      next: () => {
        window.location.reload();
      },
      error: (error) => {
        console.error('An error occurred while deleting item:', error);
      },
    });
  }
  dropAll(idc: any): void {
    this.BasketService.dropAll(idc).subscribe({
      next: () => {
        window.location.reload();
      },
      error: (error) => {
        console.error('An error occurred while deleting item:', error);
      },
    });
  }

  confirmPurcheses(idb: any): void {
    this.BasketService.confirmPurcheses(idb).subscribe({
      next: () => {
        window.location.reload();
      },
      error: (error) => {
        console.error('An error occurred while deleting item:', error);
      },
    });
  }

  apply(coupon: string, idb: any): void {
    this.BasketService.ApplyCode(coupon, idb).subscribe({
      next: (data) => {
        this.msg = data;
        if (data.msg == 'coupon invalide') {
          this.msg = '<div class="alert alert-danger" >Invalide code  </div>';
        } else {
          this.placeholder = coupon;
          this.backendData.total =
            this.backendData.total - this.backendData.total * 0.1;
          this.msg =
            '<div class="alert alert-success" > applied successfully </div>';
        }
      },
      error: (error) => {
        console.error('An error occurred while applying code:', error);
      },
    });
  }
}
