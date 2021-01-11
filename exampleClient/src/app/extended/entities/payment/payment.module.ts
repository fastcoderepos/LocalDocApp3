import { NgModule } from '@angular/core';

import {
  PaymentExtendedService,
  PaymentDetailsExtendedComponent,
  PaymentListExtendedComponent,
  PaymentNewExtendedComponent,
} from './';
import { PaymentService } from 'src/app/entities/payment';
import { PaymentModule } from 'src/app/entities/payment/payment.module';
import { paymentRoute } from './payment.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [PaymentDetailsExtendedComponent, PaymentListExtendedComponent, PaymentNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [paymentRoute, PaymentModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: PaymentService, useClass: PaymentExtendedService }],
})
export class PaymentExtendedModule {}
