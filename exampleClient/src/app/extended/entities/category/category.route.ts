import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { CategoryDetailsExtendedComponent, CategoryListExtendedComponent, CategoryNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: CategoryListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: CategoryDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: CategoryNewExtendedComponent, canActivate: [AuthGuard] },
];

export const categoryRoute: ModuleWithProviders = RouterModule.forChild(routes);
