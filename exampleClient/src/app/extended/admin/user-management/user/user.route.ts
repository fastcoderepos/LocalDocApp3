import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { UserDetailsExtendedComponent, UserListExtendedComponent, UserNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: UserListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: UserDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: UserNewExtendedComponent, canActivate: [AuthGuard] },
];

export const userRoute: ModuleWithProviders = RouterModule.forChild(routes);
