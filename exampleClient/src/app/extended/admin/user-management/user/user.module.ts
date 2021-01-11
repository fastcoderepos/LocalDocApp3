import { NgModule } from '@angular/core';

import {
  UserExtendedService,
  UserDetailsExtendedComponent,
  UserListExtendedComponent,
  UserNewExtendedComponent,
} from './';
import { UserService } from 'src/app/admin/user-management/user';
import { UserModule } from 'src/app/admin/user-management/user/user.module';
import { userRoute } from './user.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [UserDetailsExtendedComponent, UserListExtendedComponent, UserNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [userRoute, UserModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: UserService, useClass: UserExtendedService }],
})
export class UserExtendedModule {}
