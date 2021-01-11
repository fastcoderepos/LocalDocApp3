import { NgModule } from '@angular/core';

import { InventoryDetailsComponent, InventoryListComponent, InventoryNewComponent } from './';
import { inventoryRoute } from './inventory.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [InventoryDetailsComponent, InventoryListComponent, InventoryNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [inventoryRoute, SharedModule, GeneralComponentsModule],
})
export class InventoryModule {}
