import { NgModule } from '@angular/core';

import {
  UserpermissionExtendedService,
  UserpermissionDetailsExtendedComponent,
  UserpermissionListExtendedComponent,
  UserpermissionNewExtendedComponent,
} from './';
import { UserpermissionService } from 'src/app/admin/user-management/userpermission';
import { UserpermissionModule } from 'src/app/admin/user-management/userpermission/userpermission.module';
import { userpermissionRoute } from './userpermission.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [
  UserpermissionDetailsExtendedComponent,
  UserpermissionListExtendedComponent,
  UserpermissionNewExtendedComponent,
];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [userpermissionRoute, UserpermissionModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: UserpermissionService, useClass: UserpermissionExtendedService }],
})
export class UserpermissionExtendedModule {}
