import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { ActorDetailsExtendedComponent, ActorListExtendedComponent, ActorNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: ActorListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: ActorDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: ActorNewExtendedComponent, canActivate: [AuthGuard] },
];

export const actorRoute: ModuleWithProviders = RouterModule.forChild(routes);
