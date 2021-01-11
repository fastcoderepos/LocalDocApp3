import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { LanguageDetailsExtendedComponent, LanguageListExtendedComponent, LanguageNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: LanguageListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: LanguageDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: LanguageNewExtendedComponent, canActivate: [AuthGuard] },
];

export const languageRoute: ModuleWithProviders = RouterModule.forChild(routes);
