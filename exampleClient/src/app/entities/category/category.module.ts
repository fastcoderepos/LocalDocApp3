import { NgModule } from '@angular/core';

import { CategoryDetailsComponent, CategoryListComponent, CategoryNewComponent } from './';
import { categoryRoute } from './category.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [CategoryDetailsComponent, CategoryListComponent, CategoryNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [categoryRoute, SharedModule, GeneralComponentsModule],
})
export class CategoryModule {}
