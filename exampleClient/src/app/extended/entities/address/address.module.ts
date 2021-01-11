import { NgModule } from '@angular/core';

import {
  AddressExtendedService,
  AddressDetailsExtendedComponent,
  AddressListExtendedComponent,
  AddressNewExtendedComponent,
} from './';
import { AddressService } from 'src/app/entities/address';
import { AddressModule } from 'src/app/entities/address/address.module';
import { addressRoute } from './address.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [AddressDetailsExtendedComponent, AddressListExtendedComponent, AddressNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [addressRoute, AddressModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: AddressService, useClass: AddressExtendedService }],
})
export class AddressExtendedModule {}
