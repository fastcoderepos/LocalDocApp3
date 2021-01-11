import { NgModule } from '@angular/core';

import {
  StoreExtendedService,
  StoreDetailsExtendedComponent,
  StoreListExtendedComponent,
  StoreNewExtendedComponent,
} from './';
import { StoreService } from 'src/app/entities/store';
import { StoreModule } from 'src/app/entities/store/store.module';
import { storeRoute } from './store.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [StoreDetailsExtendedComponent, StoreListExtendedComponent, StoreNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [storeRoute, StoreModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: StoreService, useClass: StoreExtendedService }],
})
export class StoreExtendedModule {}
