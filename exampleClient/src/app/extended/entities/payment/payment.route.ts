import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/common/shared';
import { AuthGuard } from 'src/app/core/auth-guard';
import { PaymentDetailsExtendedComponent, PaymentListExtendedComponent, PaymentNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: PaymentListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: PaymentDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: PaymentNewExtendedComponent, canActivate: [AuthGuard] },
];

export const paymentRoute: ModuleWithProviders = RouterModule.forChild(routes);
