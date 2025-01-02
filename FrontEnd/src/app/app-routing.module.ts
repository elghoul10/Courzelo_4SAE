import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FullComponent } from './layouts/full/full.component';
import { FrontComponent } from './layouts/front/front.component';

import { LandingpageComponent } from './pages/landingpage/landingpage.component';
import { ZoomComponentViewComponent } from './manage-university/zoom-component-view/zoom-component-view.component';

import { BlankComponent } from './layouts/blank/blank.component';

import { AppSideLoginComponent } from './pages/authentication/login/login.component';
import { AppSideRegisterComponent } from './pages/authentication/register/register.component';

import { NotfoundComponent } from './pages/notfound/notfound.component';
import { BasketComponent } from './basket/basket.component';
import { AddBasketComponent } from './add-basket/add-basket.component';
import { ListPurchasesComponent } from './list-purchases/list-purchases.component';
import { ShowPurchasesComponent } from './show-purchases/show-purchases.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: FullComponent,
    children: [
      {
        path: 'clubs',
        loadChildren: () =>
          import('./manage-club/club.module').then((m) => m.ClubModule),
      },
      {
        path: '',
        loadChildren: () =>
          import('./pages/pages.module').then((m) => m.PagesModule),
      },
      {
        path: '222',
        loadChildren: () =>
          import('./manage-university/university.module').then(
            (m) => m.UniversityModule
          ),
      },
    ],
  },

  //front-----------------------
  {
    path: '',
    component: FrontComponent,
    children: [
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
      },
      {
        path: 'home',
        component: LandingpageComponent,
      },
      {
        path: 'course',
        loadChildren: () =>
          import('./manage-university/university.module').then(
            (m) => m.UniversityModule
          ),
      },

      {
        path: 'events',
        loadChildren: () =>
          import('./manage-club/club.module').then((m) => m.ClubModule),
      },
      {
        path: '',
        loadChildren: () =>
          import('./pages/authentication/authentication.module').then(
            (m) => m.AuthenticationModule
          ),
      },
    ],
  },

  {
    path: 'add_card/:idc/:idu',
    component: AddBasketComponent,
  },
  {
    path: 'purchases',
    component: ListPurchasesComponent,
  },
  {
    path: 'showDetails/:idb',
    component: ShowPurchasesComponent,
  },
  {
    path: 'showBasket/:id',
    component: BasketComponent,
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    RouterModule.forRoot([
      { path: 'meeting', component: ZoomComponentViewComponent },
    ]),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
