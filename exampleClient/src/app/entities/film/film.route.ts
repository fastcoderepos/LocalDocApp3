import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { FilmDetailsComponent, FilmListComponent, FilmNewComponent } from './';

const routes: Routes = [
  // { path: '', component: FilmListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: FilmDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: FilmNewComponent, canActivate: [ AuthGuard ] },
];

export const filmRoute: ModuleWithProviders = RouterModule.forChild(routes);
