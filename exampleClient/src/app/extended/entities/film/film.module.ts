import { NgModule } from '@angular/core';

import {
  FilmExtendedService,
  FilmDetailsExtendedComponent,
  FilmListExtendedComponent,
  FilmNewExtendedComponent,
} from './';
import { FilmService } from 'src/app/entities/film';
import { FilmModule } from 'src/app/entities/film/film.module';
import { filmRoute } from './film.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [FilmDetailsExtendedComponent, FilmListExtendedComponent, FilmNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [filmRoute, FilmModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: FilmService, useClass: FilmExtendedService }],
})
export class FilmExtendedModule {}
