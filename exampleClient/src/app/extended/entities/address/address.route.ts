import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { AddressDetailsExtendedComponent, AddressListExtendedComponent, AddressNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: AddressListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: AddressDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: AddressNewExtendedComponent, canActivate: [AuthGuard] },
];

export const addressRoute: ModuleWithProviders = RouterModule.forChild(routes);
