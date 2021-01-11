import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { CityDetailsExtendedComponent, CityListExtendedComponent, CityNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: CityListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: CityDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: CityNewExtendedComponent, canActivate: [AuthGuard] },
];

export const cityRoute: ModuleWithProviders = RouterModule.forChild(routes);
