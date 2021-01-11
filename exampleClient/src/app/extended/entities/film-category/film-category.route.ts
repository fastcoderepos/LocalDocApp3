import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import {
  FilmCategoryDetailsExtendedComponent,
  FilmCategoryListExtendedComponent,
  FilmCategoryNewExtendedComponent,
} from './';

const routes: Routes = [
  {
    path: '',
    component: FilmCategoryListExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  {
    path: ':id',
    component: FilmCategoryDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: FilmCategoryNewExtendedComponent, canActivate: [AuthGuard] },
];

export const filmCategoryRoute: ModuleWithProviders = RouterModule.forChild(routes);
