import { NgModule } from '@angular/core';

import {
  CountryExtendedService,
  CountryDetailsExtendedComponent,
  CountryListExtendedComponent,
  CountryNewExtendedComponent,
} from './';
import { CountryService } from 'src/app/entities/country';
import { CountryModule } from 'src/app/entities/country/country.module';
import { countryRoute } from './country.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [CountryDetailsExtendedComponent, CountryListExtendedComponent, CountryNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [countryRoute, CountryModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: CountryService, useClass: CountryExtendedService }],
})
export class CountryExtendedModule {}
