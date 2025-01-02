import { Universite } from 'app/manage-university/models/Universite';

export class Club {
  idClub: number | null;
  courseTitle: String;
  courseDescription: String;
  imgUrl: String;
  availablePlaces: any;
  groupTime: Date;
  meetTime: Date;
  meetUrl: String;
  evenements: any[];
  universite: any;
}
