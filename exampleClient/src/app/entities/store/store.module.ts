import { NgModule } from '@angular/core';

import { StoreDetailsComponent, StoreListComponent, StoreNewComponent } from './';
import { storeRoute } from './store.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [StoreDetailsComponent, StoreListComponent, StoreNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [storeRoute, SharedModule, GeneralComponentsModule],
})
export class StoreModule {}
