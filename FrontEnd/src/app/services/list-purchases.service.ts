import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ListPurchasesService {
  constructor(private httpClient: HttpClient) {}

  ShowAll(): Observable<any> {
    return this.httpClient.get<any>(`http://localhost:8081/pi/basket/showAll`, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }
}
