import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Club } from '../models/course';
import { Event } from '../models/event';
import { ActivatedRoute } from '@angular/router';
import { ClubService } from '../services/club.service';
import { AddEventDialogDashComponent } from '../manage-events/add-event-dialog-dash/add-event-dialog-dash.component';
import { EventService } from '../services/event.service';
import { UpdateEventDialogDashComponent } from '../manage-events/update-event-dialog-dash/update-event-dialog-dash.component';

@Component({
  selector: 'app-club-detail-dash',
  templateUrl: './club-detail-dash.component.html',
  styleUrls: ['./club-detail-dash.component.scss'],
})
export class ClubDetailDashComponent {
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
    this.clubService
      .getClub(this.activatedRoute.snapshot.params['id'])
      .subscribe({
        next: (res) => {
          console.log('API Response:', res);

          this.currentClub = res;

          // Check if there is a property that contains events data and it is an array
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

          this.dataSource = new MatTableDataSource(this.eventsList);
          this.dataSource.paginator = this.paginator;

          // Make a separate call to get events if available at a different endpoint
          this.getEvents();
        },
        error: (err) => {
          console.log(err);
        },
      });
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
}
