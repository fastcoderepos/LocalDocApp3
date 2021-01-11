import { NgModule } from '@angular/core';

import { FilmDetailsComponent, FilmListComponent, FilmNewComponent } from './';
import { filmRoute } from './film.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [FilmDetailsComponent, FilmListComponent, FilmNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [filmRoute, SharedModule, GeneralComponentsModule],
})
export class FilmModule {}
