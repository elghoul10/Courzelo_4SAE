import { Component, ViewChild, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EventService } from 'app/manage-club/services/event.service';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Event } from 'app/manage-club/models/event';
import { Subject, merge, take } from 'rxjs';
import { Club } from 'app/manage-club/models/course';

@Component({
  selector: 'app-add-event-dialog-dash',
  templateUrl: './add-event-dialog-dash.component.html',
  styleUrls: ['./add-event-dialog-dash.component.scss'],
})
export class AddEventDialogDashComponent {
  imageFile: File | undefined;
  @ViewChild('fileInput') fileInput: any;
  isFormSubmited = false;
  idCourse: any;
  brandNewEvent: Event;

  imageUrl: string | ArrayBuffer | null =
    './assets/images/profile/specDefaultImg.png';

  constructor(
    public addDialogRef: MatDialogRef<AddEventDialogDashComponent>,
    private eventService: EventService,
    private datePipe: DatePipe,
    @Inject(MAT_DIALOG_DATA) public data: Club
  ) {
    this.idCourse = this.data.idCourse;
  }

  onFileSelected(event: any): void {
    this.imageFile = event.target.files[0];
    if (this.imageFile) {
      // Read the selected image file and update the preview
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imageUrl = e.target.result;
      };
      reader.readAsDataURL(this.imageFile);
    }
  }

  triggerFileInputClick(): void {
    this.fileInput.nativeElement.click();
  }

  resetAvatarImage() {
    this.imageUrl = './assets/images/profile/specDefaultImg.png';
  }

  closeDialog(): void {
    this.addDialogRef.close();
  }

  submitForm(formData: any): void {
    formData.dateDebLesson = this.datePipe.transform(
      formData.dateDebLesson,
      'yyyy-MM-dd'
    );
    formData.dateFinLesson = this.datePipe.transform(
      formData.dateFinLesson,
      'yyyy-MM-dd'
    );

    console.log(formData);

    if (!this.imageFile) {
      formData.value.imageUrl = 'specDefaultImg.png';
    }

    this.eventService.addEvent(formData, this.idCourse).subscribe({
      next: (res) => {
        console.log(res);
        this.brandNewEvent = res;
      },
      error: (err) => {
        console.log(err);
      },
      complete: () => {
        let imageUploadCompleted = new Subject();

        if (this.imageFile) {
          this.eventService
            .uploadImage(this.brandNewEvent.idEvent, this.imageFile)
            .subscribe({
              next: (res) => {
                console.log('1_image upload********************');
                console.log(res);
              },
              error: (err) => {
                // console.log(err);
              },
              complete: () => {
                imageUploadCompleted.next(null);
                imageUploadCompleted.complete();
              },
            });
        } else {
          imageUploadCompleted.next(null);
          imageUploadCompleted.complete();
        }
        this.addDialogRef.close(formData);
      },
    });
  }
}
