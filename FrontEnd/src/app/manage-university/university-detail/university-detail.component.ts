import { ActivatedRoute, Router } from '@angular/router';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { ClubService } from '../../manage-club/services/club.service';
import { Club } from '../../manage-club/models/course';

import { Event } from '../../manage-club/models/event';

import { AddEventDialogDashComponent } from '../../manage-club/manage-events/add-event-dialog-dash/add-event-dialog-dash.component';
import { EventService } from '../../manage-club/services/event.service';
import { UpdateEventDialogDashComponent } from '../../manage-club/manage-events/update-event-dialog-dash/update-event-dialog-dash.component';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-university-detail',
  templateUrl: './university-detail.component.html',
  styleUrls: ['./university-detail.component.scss'],
})
export class UniversityDetailComponent {
  searchQuery: string = '';
  videoId: string;
  videoDetails: any;
  videoUrl: SafeResourceUrl;
  dataSource: any;
  displayedColumns: string[] = [
    'nomLesson',
    'dateDebLesson',
    'dateFinLesson',
    'descriptionLesson',
    'action',
  ];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  brandNewSpecialite: any;
  uploadUrl = 'http://localhost:8081/upload-directory/';
  currentClub: Club | null;
  eventsList: Event[];
  filtredEventsList: Event[];
  searchInput: string = '';
  searchTerm: string = '';
  definition: string = '';

  query: string;

  constructor(
    private addEventDialog: MatDialog,
    private activatedRoute: ActivatedRoute,
    private clubService: ClubService,
    private eventService: EventService,
    private updateEventDialog: MatDialog,
    private http: HttpClient,
    private sanitizer: DomSanitizer
  ) {
    this.eventsList = [];
  }

  ngOnInit(): void {
    this.refreshEventList();
  }

  refreshEventList(): void {
    this.clubService
      .getClub(this.activatedRoute.snapshot.params['id'])
      .subscribe({
        next: (res) => {
          console.log('API Response:', res);

          this.currentClub = res;
          this.searchQuery = res.courseTitle.toString();
          console.log('courseDescription:', res.courseDescription);
          this.searchVideo(this.searchQuery);
          this.searchDefinition(this.searchQuery);
          if (res.evenements && Array.isArray(res.evenements)) {
            this.eventsList = [...res.evenements];
            console.log(this.eventsList);
          } else {
            console.warn(
              "No or invalid 'evenements' property in the response. Setting eventsList to an empty array."
            );
            // Set a default value for this.eventsList or handle the absence of events data
            this.eventsList = [];
          }

          this.dataSource = new MatTableDataSource<any>(this.eventsList);
          this.dataSource.paginator = this.paginator;

          // Make a separate call to get events if available at a different endpoint
          this.getLessonsByCourseId();
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  updateCourseTitle(newValue: string): void {
    if (this.currentClub) {
      this.currentClub.courseTitle = newValue;
    }
  }

  searchDefinition(searchQuery: string) {
    this.clubService.getDefinition(this.searchQuery).subscribe(
      (result) => {
        this.definition = result;
      },
      (error) => {
        console.error('Error fetching definition:', error);
        // Handle the error in a way that makes sense for your application
      }
    );
  }

  getEvents(): void {
    this.eventService.getAllEvents().subscribe(
      (events) => {
        this.eventsList = events;
        this.dataSource = new MatTableDataSource(this.eventsList);
        this.dataSource.paginator = this.paginator;
      },
      (error) => {
        console.error('Error fetching events:', error);
      }
    );
  }

  getLessonsByCourseId(): void {
    const courseId = this.currentClub?.idCourse; // Assurez-vous que currentClub est correctement défini
    if (courseId) {
      this.eventService.getLessonsByCourseId(courseId).subscribe(
        (lessons) => {
          this.dataSource.data = lessons;
          this.dataSource.paginator = this.paginator;
        },
        (error) => {
          console.error('Error fetching lessons by course ID:', error);
        }
      );
    } else {
      console.warn('Course ID not available.');
    }
  }

  openAddEventDialog(): void {
    const dialogRef = this.addEventDialog.open(AddEventDialogDashComponent, {
      width: '550px',
      data: this.currentClub,
    });

    dialogRef.afterClosed().subscribe((result) => {
      // Handle the result after the dialog is closed (if needed)
      if (result) {
        this.refreshEventList();
        console.log('The dialog save pressed', result);
      } else {
        console.log('The dialog was closed', result);
      }
    });
  }

  onDeleteEvent(id: any): void {
    this.eventService.deleteEvent(id).subscribe(
      () => {
        this.refreshEventList();
      }
      // handle errors...
    );
  }

  openUpdateDialog(eventId: any): void {
    this.eventService.getEvent(eventId).subscribe(
      (eventData) => {
        const dialogRef = this.updateEventDialog.open(
          UpdateEventDialogDashComponent,
          {
            width: '550px',
            data: eventData,
          }
        );

        // Vous pouvez également écouter la fermeture du dialogue si nécessaire
        dialogRef.afterClosed().subscribe((result) => {
          this.refreshEventList();
          console.log('Dialog closed with result:', result);
        });
      },
      (error) => {
        console.error('Error fetching club data:', error);
      }
    );
  }

  searchVideo(searchQuery: string): void {
    const apiKey = ''; // Remplacez par votre clé d'API YouTube
    const apiUrl = `https://www.googleapis.com/youtube/v3/search?key=${apiKey}&part=snippet&type=video&q=${searchQuery}&maxResults=1`;

    this.http.get<any>(apiUrl).subscribe((data) => {
      if (data.items && data.items.length > 0) {
        const videoId = data.items[0].id.videoId;
        this.fetchVideoDetails(videoId);
      } else {
        console.log('Aucune vidéo trouvée pour la recherche : ', searchQuery);
      }
    });
  }

  fetchVideoDetails(videoId: string): void {
    const apiKey = ''; // Remplacez par votre clé d'API YouTube AIzaSyBg6KqOnNETpyStbVy31Cer3Xt38rXWnoM
    const url = `https://www.googleapis.com/youtube/v3/videos?id=${videoId}&key=${apiKey}&part=snippet,contentDetails,statistics`;

    this.http.get<any>(url).subscribe((data) => {
      if (data.items && data.items.length > 0) {
        this.videoDetails = data.items[0];
        this.videoUrl = this.sanitizer.bypassSecurityTrustResourceUrl(
          `https://www.youtube.com/embed/${videoId}`
        );
      } else {
        console.log(
          "Détails de la vidéo non disponibles pour l'ID : ",
          videoId
        );
      }
    });
  }
}
