import { NgModule } from '@angular/core';

import { FilmCategoryDetailsComponent, FilmCategoryListComponent, FilmCategoryNewComponent } from './';
import { filmCategoryRoute } from './film-category.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [FilmCategoryDetailsComponent, FilmCategoryListComponent, FilmCategoryNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [filmCategoryRoute, SharedModule, GeneralComponentsModule],
})
export class FilmCategoryModule {}
