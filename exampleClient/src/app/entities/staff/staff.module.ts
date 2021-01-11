import { NgModule } from '@angular/core';

import { StaffDetailsComponent, StaffListComponent, StaffNewComponent } from './';
import { staffRoute } from './staff.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [StaffDetailsComponent, StaffListComponent, StaffNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [staffRoute, SharedModule, GeneralComponentsModule],
})
export class StaffModule {}
