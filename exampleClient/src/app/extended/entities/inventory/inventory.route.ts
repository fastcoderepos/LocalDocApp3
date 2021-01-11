import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { InventoryDetailsExtendedComponent, InventoryListExtendedComponent, InventoryNewExtendedComponent } from './';

const routes: Routes = [
  {
    path: '',
    component: InventoryListExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  {
    path: ':id',
    component: InventoryDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: InventoryNewExtendedComponent, canActivate: [AuthGuard] },
];

export const inventoryRoute: ModuleWithProviders = RouterModule.forChild(routes);
