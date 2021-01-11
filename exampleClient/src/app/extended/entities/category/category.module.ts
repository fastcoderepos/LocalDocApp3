import { NgModule } from '@angular/core';

import {
  CategoryExtendedService,
  CategoryDetailsExtendedComponent,
  CategoryListExtendedComponent,
  CategoryNewExtendedComponent,
} from './';
import { CategoryService } from 'src/app/entities/category';
import { CategoryModule } from 'src/app/entities/category/category.module';
import { categoryRoute } from './category.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [CategoryDetailsExtendedComponent, CategoryListExtendedComponent, CategoryNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [categoryRoute, CategoryModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: CategoryService, useClass: CategoryExtendedService }],
})
export class CategoryExtendedModule {}
