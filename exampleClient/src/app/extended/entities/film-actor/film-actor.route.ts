import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { FilmActorDetailsExtendedComponent, FilmActorListExtendedComponent, FilmActorNewExtendedComponent } from './';

const routes: Routes = [
  {
    path: '',
    component: FilmActorListExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  {
    path: ':id',
    component: FilmActorDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: FilmActorNewExtendedComponent, canActivate: [AuthGuard] },
];

export const filmActorRoute: ModuleWithProviders = RouterModule.forChild(routes);
