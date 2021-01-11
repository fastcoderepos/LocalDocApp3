import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { StoreDetailsComponent, StoreListComponent, StoreNewComponent } from './';

const routes: Routes = [
  // { path: '', component: StoreListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: StoreDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: StoreNewComponent, canActivate: [ AuthGuard ] },
];

export const storeRoute: ModuleWithProviders = RouterModule.forChild(routes);
