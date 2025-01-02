import {
  Component,
  ViewChildren,
  ElementRef,
  AfterViewInit,
  QueryList,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClubService } from './../../manage-club/services/club.service';

@Component({
  selector: 'app-pub',
  templateUrl: './pub.component.html',
  styleUrls: ['./pub.component.scss'],
})
export class PubComponent implements AfterViewInit {
  @ViewChildren('star') starElements!: QueryList<ElementRef>;
  @ViewChild('output') outputElement!: ElementRef;
  private idCourse: string;

  constructor(
    private clubService: ClubService,
    private route: ActivatedRoute
  ) {}

  ngAfterViewInit(): void {
    this.initializeStars();
  }

  initializeStars(): void {
    this.starElements.forEach((star) => {
      star.nativeElement.classList.add('star');
    });
  }

  setRating(n: number): void {
    this.starElements.forEach((star, index) => {
      star.nativeElement.classList.remove(
        'one',
        'two',
        'three',
        'four',
        'five'
      );
      if (index < n) {
        star.nativeElement.classList.add(this.getStarClass(n));
      }
    });

    if (this.outputElement) {
      this.outputElement.nativeElement.innerText = `Rating is: ${n}/5`;
    }

    this.route.paramMap.subscribe((params) => {
      const idCourse = params.get('id');
      if (idCourse) {
        const userId = '1'; // Exemple d'identifiant utilisateur (à adapter selon votre logique)
        const courseId = parseInt(idCourse, 10); // Convertir l'identifiant du cours en nombre

        if (!isNaN(courseId)) {
          // Appeler la méthode saveRating du service ClubService avec userId, courseId et la notation (n)
          this.clubService.saveRating(userId, courseId, n).subscribe(
            (response: any) => {
              console.log('Rating saved successfully:', response);
              // Traitez la réponse ici si nécessaire
            },
            (error: any) => {
              console.error('Error saving rating:', error);
              // Affichez ou gérez l'erreur ici de manière appropriée
            }
          );
        } else {
          console.error('Invalid courseId:', idCourse);
        }
      } else {
        console.error('idCourse is null');
      }
    });
  }

  getStarClass(n: number): string {
    switch (n) {
      case 1:
        return 'one';
      case 2:
        return 'two';
      case 3:
        return 'three';
      case 4:
        return 'four';
      case 5:
        return 'five';
      default:
        return '';
    }
  }
}
