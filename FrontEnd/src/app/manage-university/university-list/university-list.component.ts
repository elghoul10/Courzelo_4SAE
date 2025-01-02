import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { ClubService } from '../../manage-club/services/club.service';
import { Club } from '../../manage-club/models/course';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-university-list',
  templateUrl: './university-list.component.html',
  styleUrls: ['./university-list.component.scss'],
})
export class UniversityListComponent implements OnInit, AfterViewInit {
  filtredClubsList: Club[];
  searchInput: string = '';

  constructor(
    private addClubDialog: MatDialog,
    private clubService: ClubService,
    private ac: ActivatedRoute,
    private router: Router,
    private updateClubDialog: MatDialog
  ) {}

  dataSource = new MatTableDataSource<Club>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.refreshClubList();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  refreshClubList(): void {
    this.clubService.getAllClubs().subscribe(
      (clubs) => {
        this.dataSource.data = clubs;
        this.dataSource.paginator = this.paginator;

        clubs.forEach((club) => {
          // Vérifier si club.idCourse est non null avant d'appeler getAverageRatingForClub
          if (club.idCourse !== null) {
            this.clubService.getAverageRatingForClub(club.idCourse).subscribe(
              (averageRating) => {
                club.averageRating = averageRating;
              },
              (error) => {
                console.error(
                  "Une erreur s'est produite lors de la récupération de la moyenne des évaluations :",
                  error
                );
              }
            );
          } else {
            // Gérer le cas où club.idCourse est null (par exemple, affecter une valeur par défaut)
            club.averageRating = 0; // Ou toute autre valeur par défaut
          }
        });
      },
      (error) => {
        console.error(
          "Une erreur s'est produite lors de la récupération des clubs:",
          error
        );
      }
    );
  }

  onDeleteClub(id: any): void {
    this.clubService.deleteClub(id).subscribe(
      () => {
        this.refreshClubList();
      }
      // handle errors...
    );
  }

  onSearchChange(searchInput: string): void {
    if (!searchInput) {
      this.refreshClubList();
      return;
    }

    this.filtredClubsList = this.dataSource.data.filter(
      (spec) =>
        spec.courseTitle.toLowerCase().includes(searchInput.toLowerCase()) ||
        spec.courseDescription
          .toLowerCase()
          .includes(searchInput.toLowerCase()) ||
        spec.specialite.nomUniversite
          .toLowerCase()
          .includes(searchInput.toLowerCase())
    );

    this.dataSource = new MatTableDataSource(this.filtredClubsList);
    this.dataSource.paginator = this.paginator;
  }

  onCategoryChange(): void {
    const checkboxes: NodeListOf<HTMLInputElement> =
      document.querySelectorAll('.m-check-input');

    checkboxes.forEach((checkbox) => {
      checkbox.addEventListener('change', () => {
        const selectedCategories: string[] = [];

        checkboxes.forEach((checkbox) => {
          if (checkbox.checked) {
            selectedCategories.push(checkbox.id);
          }
        });

        this.filtredClubsList = this.dataSource.data.filter((club) =>
          selectedCategories.some((specialite) =>
            club.specialite.includes(specialite)
          )
        );

        this.dataSource = new MatTableDataSource(this.filtredClubsList);
        this.dataSource.paginator = this.paginator;
      });
    });
  }
}
