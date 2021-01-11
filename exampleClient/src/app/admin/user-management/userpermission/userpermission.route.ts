import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { UserpermissionDetailsComponent, UserpermissionListComponent, UserpermissionNewComponent } from './';

const routes: Routes = [
  // { path: '', component: UserpermissionListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: UserpermissionDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: UserpermissionNewComponent, canActivate: [ AuthGuard ] },
];

export const userpermissionRoute: ModuleWithProviders = RouterModule.forChild(routes);
