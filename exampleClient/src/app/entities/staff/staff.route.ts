import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { StaffDetailsComponent, StaffListComponent, StaffNewComponent } from './';

const routes: Routes = [
  // { path: '', component: StaffListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: StaffDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: StaffNewComponent, canActivate: [ AuthGuard ] },
];

export const staffRoute: ModuleWithProviders = RouterModule.forChild(routes);
