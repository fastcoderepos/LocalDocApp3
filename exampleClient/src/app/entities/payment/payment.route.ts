import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/common/shared';
// import { AuthGuard } from 'src/app/core/auth-guard';

// import { PaymentDetailsComponent, PaymentListComponent, PaymentNewComponent } from './';

const routes: Routes = [
  // { path: '', component: PaymentListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: PaymentDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: PaymentNewComponent, canActivate: [ AuthGuard ] },
];

export const paymentRoute: ModuleWithProviders = RouterModule.forChild(routes);
