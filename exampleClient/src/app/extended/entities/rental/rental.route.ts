import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { RentalDetailsExtendedComponent, RentalListExtendedComponent, RentalNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: RentalListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: RentalDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: RentalNewExtendedComponent, canActivate: [AuthGuard] },
];

export const rentalRoute: ModuleWithProviders = RouterModule.forChild(routes);
