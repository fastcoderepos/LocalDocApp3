import { NgModule } from '@angular/core';

import { FilmActorDetailsComponent, FilmActorListComponent, FilmActorNewComponent } from './';
import { filmActorRoute } from './film-actor.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [FilmActorDetailsComponent, FilmActorListComponent, FilmActorNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [filmActorRoute, SharedModule, GeneralComponentsModule],
})
export class FilmActorModule {}
