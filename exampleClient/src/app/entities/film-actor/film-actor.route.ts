import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { FilmActorDetailsComponent, FilmActorListComponent, FilmActorNewComponent } from './';

const routes: Routes = [
  // { path: '', component: FilmActorListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: FilmActorDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: FilmActorNewComponent, canActivate: [ AuthGuard ] },
];

export const filmActorRoute: ModuleWithProviders = RouterModule.forChild(routes);
