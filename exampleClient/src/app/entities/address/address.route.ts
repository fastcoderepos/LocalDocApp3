import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { AddressDetailsComponent, AddressListComponent, AddressNewComponent } from './';

const routes: Routes = [
  // { path: '', component: AddressListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: AddressDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: AddressNewComponent, canActivate: [ AuthGuard ] },
];

export const addressRoute: ModuleWithProviders = RouterModule.forChild(routes);
