import { NgModule } from '@angular/core';

import {
  ActorExtendedService,
  ActorDetailsExtendedComponent,
  ActorListExtendedComponent,
  ActorNewExtendedComponent,
} from './';
import { ActorService } from 'src/app/entities/actor';
import { ActorModule } from 'src/app/entities/actor/actor.module';
import { actorRoute } from './actor.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [ActorDetailsExtendedComponent, ActorListExtendedComponent, ActorNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [actorRoute, ActorModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: ActorService, useClass: ActorExtendedService }],
})
export class ActorExtendedModule {}
