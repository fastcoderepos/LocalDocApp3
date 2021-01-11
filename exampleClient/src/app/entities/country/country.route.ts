import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { CountryDetailsComponent, CountryListComponent, CountryNewComponent } from './';

const routes: Routes = [
  // { path: '', component: CountryListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: CountryDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: CountryNewComponent, canActivate: [ AuthGuard ] },
];

export const countryRoute: ModuleWithProviders = RouterModule.forChild(routes);
