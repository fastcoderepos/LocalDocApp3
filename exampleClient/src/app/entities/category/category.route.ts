import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { CategoryDetailsComponent, CategoryListComponent, CategoryNewComponent } from './';

const routes: Routes = [
  // { path: '', component: CategoryListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: CategoryDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: CategoryNewComponent, canActivate: [ AuthGuard ] },
];

export const categoryRoute: ModuleWithProviders = RouterModule.forChild(routes);
