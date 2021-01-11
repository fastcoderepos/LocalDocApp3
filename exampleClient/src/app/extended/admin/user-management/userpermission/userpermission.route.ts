import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import {
  UserpermissionDetailsExtendedComponent,
  UserpermissionListExtendedComponent,
  UserpermissionNewExtendedComponent,
} from './';

const routes: Routes = [
  {
    path: '',
    component: UserpermissionListExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  {
    path: ':id',
    component: UserpermissionDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: UserpermissionNewExtendedComponent, canActivate: [AuthGuard] },
];

export const userpermissionRoute: ModuleWithProviders = RouterModule.forChild(routes);
