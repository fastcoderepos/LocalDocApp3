import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { RentalDetailsComponent, RentalListComponent, RentalNewComponent } from './';

const routes: Routes = [
  // { path: '', component: RentalListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: RentalDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: RentalNewComponent, canActivate: [ AuthGuard ] },
];

export const rentalRoute: ModuleWithProviders = RouterModule.forChild(routes);
