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

@Component({
  selector: 'app-university-list-dash',
  templateUrl: './university-list-dash.component.html',
  styleUrls: ['./university-list-dash.component.scss'],
})
export class UniversityListDashComponent {
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
  currentLesson: any | null;
  eventsList: Event[];
  filtredEventsList: Event[];
  searchInput: string = '';

  constructor(
    private addEventDialog: MatDialog,
    private activatedRoute: ActivatedRoute,
    private clubService: ClubService,
    private eventService: EventService,
    private updateEventDialog: MatDialog
  ) {
    this.eventsList = [];
  }

  ngOnInit(): void {
    this.refreshEventList();
  }

  refreshEventList(): void {
    this.eventService
      .getEvent(this.activatedRoute.snapshot.params['id'])
      .subscribe({
        next: (res) => {
          console.log('API Response:', res);

          const eventData = res;

          this.currentLesson = eventData;
          this.getEvents();
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  getVideoUrl(): string {
    if (this.currentLesson && this.currentLesson.imageLesson) {
      const videoFileName = this.currentLesson.imageLesson;
      const idEvent = this.currentLesson.idEvent;
      return `${this.uploadUrl}/${idEvent}/videos/${videoFileName}`;
    }
    return 'path/to/default/video.mp4';
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

  openAddEventDialog(): void {
    const dialogRef = this.addEventDialog.open(AddEventDialogDashComponent, {
      width: '550px',
      data: this.currentLesson,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.refreshEventList();
        console.log('The dialog save pressed', result);
      } else {
        console.log('The dialog was closed', result);
      }
    });
  }

  onDeleteEvent(id: any): void {
    this.eventService.deleteEvent(id).subscribe(() => {
      this.refreshEventList();
    });
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
}
