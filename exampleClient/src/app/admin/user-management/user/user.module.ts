import { NgModule } from '@angular/core';

import { UserDetailsComponent, UserListComponent, UserNewComponent } from './';
import { userRoute } from './user.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [UserDetailsComponent, UserListComponent, UserNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [userRoute, SharedModule, GeneralComponentsModule],
})
export class UserModule {}
