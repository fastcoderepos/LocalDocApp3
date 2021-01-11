import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { ActorDetailsComponent, ActorListComponent, ActorNewComponent } from './';

const routes: Routes = [
  // { path: '', component: ActorListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: ActorDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: ActorNewComponent, canActivate: [ AuthGuard ] },
];

export const actorRoute: ModuleWithProviders = RouterModule.forChild(routes);
