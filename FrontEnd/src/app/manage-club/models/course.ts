import { Specialite } from 'app/manage-university/models/specialite';

export class Club {
  idCourse: number | null;
  courseTitle: any;
  courseDescription: String;
  imgUrl: String;
  groupTime: Date;
  meetTime: Date;
  meetUrl: Date;
  prix: number;
  evenements: any[];
  specialite: any;
  averageRating: any;
}
