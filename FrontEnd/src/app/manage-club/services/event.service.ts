import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from '../models/event';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {}

  addEvent(data: any, id: any): Observable<Event> {
    console.log('data');
    console.log(data);
    return this.http.post<Event>(
      `${this.baseUrl}/dashboard/clubs/addEvent/${id}`,
      data
    );
  }

  uploadImage(id: any, file: File): Observable<Event> {
    const formData: FormData = new FormData();
    formData.append('fileImage', file);

    return this.http.post<Event>(
      `${this.baseUrl}/dashboard/clubs/events/uploadImage/${id}`,
      formData
    );
  }

  deleteEvent(id: any) {
    return this.http.delete(
      `${this.baseUrl}/dashboard/clubs/deleteEvent/${id}`
    );
  }

  getEvent(id: any): Observable<Event> {
    return this.http.get<Event>(
      `${this.baseUrl}/dashboard/clubs/getOneEvent/${id}`
    );
  }

  updateEvent(data: any, id: any): Observable<Event> {
    console.log('data');
    console.log(data);
    return this.http.put<Event>(
      `${this.baseUrl}/dashboard/clubs/updateEvent/${id}`,
      data
    );
  }

  getAllEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(`${this.baseUrl}/events/front`);
  }

  getLessonsByCourseId(idCourse: number): Observable<Event[]> {
    return this.http.get<Event[]>(`${this.baseUrl}/dashboard/clubs/getLessonsByCourseId/${idCourse}`);
  }
}
