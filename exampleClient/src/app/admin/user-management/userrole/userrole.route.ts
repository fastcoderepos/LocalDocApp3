import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { UserroleDetailsComponent, UserroleListComponent, UserroleNewComponent } from './';

const routes: Routes = [
  // { path: '', component: UserroleListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: UserroleDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: UserroleNewComponent, canActivate: [ AuthGuard ] },
];

export const userroleRoute: ModuleWithProviders = RouterModule.forChild(routes);
