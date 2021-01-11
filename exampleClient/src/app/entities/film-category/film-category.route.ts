import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { FilmCategoryDetailsComponent, FilmCategoryListComponent, FilmCategoryNewComponent } from './';

const routes: Routes = [
  // { path: '', component: FilmCategoryListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: FilmCategoryDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: FilmCategoryNewComponent, canActivate: [ AuthGuard ] },
];

export const filmCategoryRoute: ModuleWithProviders = RouterModule.forChild(routes);
