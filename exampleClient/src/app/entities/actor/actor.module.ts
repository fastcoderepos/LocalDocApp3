import { NgModule } from '@angular/core';

import { ActorDetailsComponent, ActorListComponent, ActorNewComponent } from './';
import { actorRoute } from './actor.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [ActorDetailsComponent, ActorListComponent, ActorNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [actorRoute, SharedModule, GeneralComponentsModule],
})
export class ActorModule {}
