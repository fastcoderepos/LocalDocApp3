import { NgModule } from '@angular/core';

import {
  FilmCategoryExtendedService,
  FilmCategoryDetailsExtendedComponent,
  FilmCategoryListExtendedComponent,
  FilmCategoryNewExtendedComponent,
} from './';
import { FilmCategoryService } from 'src/app/entities/film-category';
import { FilmCategoryModule } from 'src/app/entities/film-category/film-category.module';
import { filmCategoryRoute } from './film-category.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [
  FilmCategoryDetailsExtendedComponent,
  FilmCategoryListExtendedComponent,
  FilmCategoryNewExtendedComponent,
];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [filmCategoryRoute, FilmCategoryModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: FilmCategoryService, useClass: FilmCategoryExtendedService }],
})
export class FilmCategoryExtendedModule {}
