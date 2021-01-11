import { NgModule } from '@angular/core';

import {
  StaffExtendedService,
  StaffDetailsExtendedComponent,
  StaffListExtendedComponent,
  StaffNewExtendedComponent,
} from './';
import { StaffService } from 'src/app/entities/staff';
import { StaffModule } from 'src/app/entities/staff/staff.module';
import { staffRoute } from './staff.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [StaffDetailsExtendedComponent, StaffListExtendedComponent, StaffNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [staffRoute, StaffModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: StaffService, useClass: StaffExtendedService }],
})
export class StaffExtendedModule {}
