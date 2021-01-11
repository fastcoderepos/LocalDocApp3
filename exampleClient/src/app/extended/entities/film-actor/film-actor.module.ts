import { NgModule } from '@angular/core';

import {
  FilmActorExtendedService,
  FilmActorDetailsExtendedComponent,
  FilmActorListExtendedComponent,
  FilmActorNewExtendedComponent,
} from './';
import { FilmActorService } from 'src/app/entities/film-actor';
import { FilmActorModule } from 'src/app/entities/film-actor/film-actor.module';
import { filmActorRoute } from './film-actor.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [FilmActorDetailsExtendedComponent, FilmActorListExtendedComponent, FilmActorNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [filmActorRoute, FilmActorModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: FilmActorService, useClass: FilmActorExtendedService }],
})
export class FilmActorExtendedModule {}
