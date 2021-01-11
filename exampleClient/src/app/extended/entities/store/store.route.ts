import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { StoreDetailsExtendedComponent, StoreListExtendedComponent, StoreNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: StoreListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: StoreDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: StoreNewExtendedComponent, canActivate: [AuthGuard] },
];

export const storeRoute: ModuleWithProviders = RouterModule.forChild(routes);
