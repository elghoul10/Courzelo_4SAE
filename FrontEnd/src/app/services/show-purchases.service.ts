import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ShowPurchasesService {
  constructor(private httpClient: HttpClient) {}

  ShowPurchases(id: any): Observable<any> {
    return this.httpClient.get<any>(
      `http://localhost:8081/pi/purchase/getPurchases?idb=${id}`,
      {
        headers: new HttpHeaders({
          // 'Authorization' : `Bearer ${localStorage.getItem('token')}`
        }),
      }
    );
  }
}
