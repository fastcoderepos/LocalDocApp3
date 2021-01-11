import { NgModule } from '@angular/core';

import { LanguageDetailsComponent, LanguageListComponent, LanguageNewComponent } from './';
import { languageRoute } from './language.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [LanguageDetailsComponent, LanguageListComponent, LanguageNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [languageRoute, SharedModule, GeneralComponentsModule],
})
export class LanguageModule {}
