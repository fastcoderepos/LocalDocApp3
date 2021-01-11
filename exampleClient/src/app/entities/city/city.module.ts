import { NgModule } from '@angular/core';

import { CityDetailsComponent, CityListComponent, CityNewComponent } from './';
import { cityRoute } from './city.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [CityDetailsComponent, CityListComponent, CityNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [cityRoute, SharedModule, GeneralComponentsModule],
})
export class CityModule {}
