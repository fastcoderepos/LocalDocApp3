import { NgModule } from '@angular/core';

import { UserpermissionDetailsComponent, UserpermissionListComponent, UserpermissionNewComponent } from './';
import { userpermissionRoute } from './userpermission.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [UserpermissionDetailsComponent, UserpermissionListComponent, UserpermissionNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [userpermissionRoute, SharedModule, GeneralComponentsModule],
})
export class UserpermissionModule {}
