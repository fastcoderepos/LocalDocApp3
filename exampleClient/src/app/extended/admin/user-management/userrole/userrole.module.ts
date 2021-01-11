import { NgModule } from '@angular/core';

import {
  UserroleExtendedService,
  UserroleDetailsExtendedComponent,
  UserroleListExtendedComponent,
  UserroleNewExtendedComponent,
} from './';
import { UserroleService } from 'src/app/admin/user-management/userrole';
import { UserroleModule } from 'src/app/admin/user-management/userrole/userrole.module';
import { userroleRoute } from './userrole.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [UserroleDetailsExtendedComponent, UserroleListExtendedComponent, UserroleNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [userroleRoute, UserroleModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: UserroleService, useClass: UserroleExtendedService }],
})
export class UserroleExtendedModule {}
