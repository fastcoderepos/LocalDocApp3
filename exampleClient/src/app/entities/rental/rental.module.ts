import { NgModule } from '@angular/core';

import { RentalDetailsComponent, RentalListComponent, RentalNewComponent } from './';
import { rentalRoute } from './rental.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [RentalDetailsComponent, RentalListComponent, RentalNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [rentalRoute, SharedModule, GeneralComponentsModule],
})
export class RentalModule {}
