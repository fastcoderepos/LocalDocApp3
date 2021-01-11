import { NgModule } from '@angular/core';

import { AddressDetailsComponent, AddressListComponent, AddressNewComponent } from './';
import { addressRoute } from './address.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [AddressDetailsComponent, AddressListComponent, AddressNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [addressRoute, SharedModule, GeneralComponentsModule],
})
export class AddressModule {}
