import { NgModule } from '@angular/core';

import {
  CityExtendedService,
  CityDetailsExtendedComponent,
  CityListExtendedComponent,
  CityNewExtendedComponent,
} from './';
import { CityService } from 'src/app/entities/city';
import { CityModule } from 'src/app/entities/city/city.module';
import { cityRoute } from './city.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [CityDetailsExtendedComponent, CityListExtendedComponent, CityNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [cityRoute, CityModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: CityService, useClass: CityExtendedService }],
})
export class CityExtendedModule {}
