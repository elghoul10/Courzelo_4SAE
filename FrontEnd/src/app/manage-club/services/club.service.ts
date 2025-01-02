import { Injectable } from '@angular/core';
import { Club } from '../models/course';
import { Rating } from '../models/Rating';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ClubService {
  private baseUrl = 'http://localhost:8081';
  private baseUrl1 = 'http://localhost:8081';

  constructor(private http: HttpClient) {}

  getAllClubs(): Observable<Club[]> {
    return this.http.get<Club[]>(`${this.baseUrl}/dashboard/clubs`);
  }

  deleteClub(id: any) {
    return this.http.delete(`${this.baseUrl}/dashboard/clubs/deleteClub/${id}`);
  }

  addClub(data: any): Observable<Club> {
    console.log('data');
    console.log(data);
    return this.http.post<Club>(
      this.baseUrl + '/dashboard/clubs/addClub/1/' + data.university,
      data
    );
  }

  uploadImage(id: any, file: File): Observable<Club> {
    const formData: FormData = new FormData();
    formData.append('fileImage', file);

    return this.http.post<Club>(
      `${this.baseUrl}/dashboard/clubs/uploadImage/${id}`,
      formData
    );
  }

  getClub(id: any): Observable<Club> {
    return this.http.get<Club>(
      `${this.baseUrl}/dashboard/clubs/getOneClub/${id}`
    );
  }

  updateClub(data: any): Observable<Club> {
    console.log('data');
    console.log(data);
    return this.http.put<Club>(
      this.baseUrl + '/dashboard/clubs/updateClub/' + data.university,
      data
    );
  }

  shareFb(id: any): Observable<string> {
    const shareFbEndpoint = `${this.baseUrl}/shareFb/${id}`;

    return this.http.post<string>(shareFbEndpoint, null);
  }

  getDefinition(query: string): Observable<string> {
    const url = `${this.baseUrl1}/azz/${query}`;
    return this.http.get(url, { responseType: 'text' });
  }

  searchVideo(query: string): Observable<string> {
    const apiUrl = `${this.baseUrl1}/api/searchVideo?query=${query}`;
    return this.http.get<string>(apiUrl);
  }

  saveRating(
    userId: string,
    courseId: number,
    ratingValue: number
  ): Observable<any> {
    const payload = {
      idUser: userId,
      ratingValue: ratingValue,
      course: {
        idClub: courseId,
      },
    };

    return this.http.post<any>(
      `${this.baseUrl1}/saveRating/${courseId}`,
      payload
    );
  }

  getAverageRatingForClub(idClub: number): Observable<number> {
    const url = `${this.baseUrl}/clubs/averageRating/${idClub}`;
    return this.http.get<number>(url);
  }

  getMonthlyRevenue(userId: number): Observable<number[]> {
    const url = `${this.baseUrl}/monthlyRevenue/${userId}`;
    return this.http.get<number[]>(url);
  }
}
