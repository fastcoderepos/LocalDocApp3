import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { LanguageDetailsComponent, LanguageListComponent, LanguageNewComponent } from './';

const routes: Routes = [
  // { path: '', component: LanguageListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: LanguageDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: LanguageNewComponent, canActivate: [ AuthGuard ] },
];

export const languageRoute: ModuleWithProviders = RouterModule.forChild(routes);
