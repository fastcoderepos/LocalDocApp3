import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { CityDetailsComponent, CityListComponent, CityNewComponent } from './';

const routes: Routes = [
  // { path: '', component: CityListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: CityDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: CityNewComponent, canActivate: [ AuthGuard ] },
];

export const cityRoute: ModuleWithProviders = RouterModule.forChild(routes);
