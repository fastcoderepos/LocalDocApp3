import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { UserDetailsComponent, UserListComponent, UserNewComponent } from './';

const routes: Routes = [
  // { path: '', component: UserListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: UserDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: UserNewComponent, canActivate: [ AuthGuard ] },
];

export const userRoute: ModuleWithProviders = RouterModule.forChild(routes);
