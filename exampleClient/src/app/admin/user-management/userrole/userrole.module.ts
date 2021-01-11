import { NgModule } from '@angular/core';

import { UserroleDetailsComponent, UserroleListComponent, UserroleNewComponent } from './';
import { userroleRoute } from './userrole.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [UserroleDetailsComponent, UserroleListComponent, UserroleNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [userroleRoute, SharedModule, GeneralComponentsModule],
})
export class UserroleModule {}
