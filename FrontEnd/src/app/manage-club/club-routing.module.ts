import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventDetailsComponent } from './event-details/event-details.component';
import { ClubListDashComponent } from './club-list-dash/club-list-dash.component';
import { DeleteClubComponent } from './delete-club/delete-club.component';
import { ClubDetailDashComponent } from './club-detail-dash/club-detail-dash.component';
import { EventListFrontComponent } from './manage-events/event-list-front/event-list-front.component';

const routes: Routes = [
  {
    path: '',
    component: ClubListDashComponent,
  },
  {
    path: 'detail/:id',
    component: ClubDetailDashComponent,
  },
  {
    path: 'front',
    children: [
      {
        path: '',
        component: EventListFrontComponent,
      },
      {
        path: 'detail/:id',
        component: EventDetailsComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClubRoutingModule {}
