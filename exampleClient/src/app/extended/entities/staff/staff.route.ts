import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { StaffDetailsExtendedComponent, StaffListExtendedComponent, StaffNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: StaffListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: StaffDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: StaffNewExtendedComponent, canActivate: [AuthGuard] },
];

export const staffRoute: ModuleWithProviders = RouterModule.forChild(routes);
