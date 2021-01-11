import { NgModule } from '@angular/core';

import {
  RentalExtendedService,
  RentalDetailsExtendedComponent,
  RentalListExtendedComponent,
  RentalNewExtendedComponent,
} from './';
import { RentalService } from 'src/app/entities/rental';
import { RentalModule } from 'src/app/entities/rental/rental.module';
import { rentalRoute } from './rental.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [RentalDetailsExtendedComponent, RentalListExtendedComponent, RentalNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [rentalRoute, RentalModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: RentalService, useClass: RentalExtendedService }],
})
export class RentalExtendedModule {}
