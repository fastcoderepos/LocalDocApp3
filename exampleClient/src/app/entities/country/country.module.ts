import { NgModule } from '@angular/core';

import { CountryDetailsComponent, CountryListComponent, CountryNewComponent } from './';
import { countryRoute } from './country.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [CountryDetailsComponent, CountryListComponent, CountryNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [countryRoute, SharedModule, GeneralComponentsModule],
})
export class CountryModule {}
