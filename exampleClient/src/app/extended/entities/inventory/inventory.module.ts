import { NgModule } from '@angular/core';

import {
  InventoryExtendedService,
  InventoryDetailsExtendedComponent,
  InventoryListExtendedComponent,
  InventoryNewExtendedComponent,
} from './';
import { InventoryService } from 'src/app/entities/inventory';
import { InventoryModule } from 'src/app/entities/inventory/inventory.module';
import { inventoryRoute } from './inventory.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [InventoryDetailsExtendedComponent, InventoryListExtendedComponent, InventoryNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [inventoryRoute, InventoryModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: InventoryService, useClass: InventoryExtendedService }],
})
export class InventoryExtendedModule {}
