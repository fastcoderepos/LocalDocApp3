import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { CountryDetailsExtendedComponent, CountryListExtendedComponent, CountryNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: CountryListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: CountryDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: CountryNewExtendedComponent, canActivate: [AuthGuard] },
];

export const countryRoute: ModuleWithProviders = RouterModule.forChild(routes);
