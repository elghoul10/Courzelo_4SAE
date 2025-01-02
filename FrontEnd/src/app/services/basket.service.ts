import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BasketService {
  constructor(private httpClient: HttpClient) {}

  ShowBasket(varValue: any): Observable<any> {
    return this.httpClient.get<any>(
      `http://localhost:8081/pi/basket/showBasket?idU=${varValue}`,
      {
        headers: new HttpHeaders({
          // 'Authorization' : `Bearer ${localStorage.getItem('token')}`
        }),
      }
    );
  }

  Search(varValue: any, date: any): Observable<any> {
    return this.httpClient.get<any>(
      `http://localhost:8081/pi/basket/Search?key=${varValue}&date=${date}`,
      {
        headers: new HttpHeaders({
          // 'Authorization' : `Bearer ${localStorage.getItem('token')}`
        }),
      }
    );
  }

  deleteItemFromBasket(idc: any, idb: any): Observable<any> {
    return this.httpClient.delete<any>(
      `http://localhost:8081/pi/basket/dropItem?idc=${idc}&idb=${idb}`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
      }
    );
  }

  dropAll(idb: any): Observable<any> {
    return this.httpClient.delete<any>(
      `http://localhost:8081/pi/basket/emptyBasket?idb=${idb}`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
      }
    );
  }

  confirmPurcheses(idb: any): Observable<any> {
    return this.httpClient.post<any>(
      `http://localhost:8081/pi/purchase/confirmPurchases?idb=${idb}`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
      }
    );
  }

  ApplyCode(code: any, idb: any): Observable<any> {
    return this.httpClient.get<any>(
      `http://localhost:8081/pi/basket/ApplyCode?code=${code}&idb=${idb}`,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
      }
    );
  }
}
