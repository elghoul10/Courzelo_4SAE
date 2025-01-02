import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AddBasketService } from 'app/services/add-basket.service';

@Component({
  selector: 'app-add-basket',
  templateUrl: './add-basket.component.html',
  styleUrls: ['./add-basket.component.scss']
})
export class AddBasketComponent implements OnInit {
  idc : any;
  idu : any;
  constructor(private AddBasketService: AddBasketService,private activaterouter:ActivatedRoute,private router: Router) { }
  ngOnInit(): void {

    this.idc=this.activaterouter.snapshot.params['idc'];
    this.idu=this.activaterouter.snapshot.params['idu'];
    this.addItemToBasket(this.idc,this.idu)
  }

  addItemToBasket(idc: any, idU: any): void {
    this.AddBasketService.addItemToBasket(idc, idU).subscribe({
      next: () => {
        this.router.navigate(['/showBasket/1'])
      },
      error: (error) => {
        console.error('An error occurred while deleting item:', error);
      }
    });
  };

}
