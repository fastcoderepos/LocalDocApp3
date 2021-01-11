import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { InventoryDetailsComponent, InventoryListComponent, InventoryNewComponent } from './';

const routes: Routes = [
  // { path: '', component: InventoryListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: InventoryDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: InventoryNewComponent, canActivate: [ AuthGuard ] },
];

export const inventoryRoute: ModuleWithProviders = RouterModule.forChild(routes);
