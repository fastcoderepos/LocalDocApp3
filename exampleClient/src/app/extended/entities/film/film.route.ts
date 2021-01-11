import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { FilmDetailsExtendedComponent, FilmListExtendedComponent, FilmNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: FilmListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: FilmDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: FilmNewExtendedComponent, canActivate: [AuthGuard] },
];

export const filmRoute: ModuleWithProviders = RouterModule.forChild(routes);
