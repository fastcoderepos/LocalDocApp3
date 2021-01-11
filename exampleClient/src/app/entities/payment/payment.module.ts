import { NgModule } from '@angular/core';

import { PaymentDetailsComponent, PaymentListComponent, PaymentNewComponent } from './';
import { paymentRoute } from './payment.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [PaymentDetailsComponent, PaymentListComponent, PaymentNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [paymentRoute, SharedModule, GeneralComponentsModule],
})
export class PaymentModule {}
