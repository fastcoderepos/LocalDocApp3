import { NgModule } from '@angular/core';

import {
  LanguageExtendedService,
  LanguageDetailsExtendedComponent,
  LanguageListExtendedComponent,
  LanguageNewExtendedComponent,
} from './';
import { LanguageService } from 'src/app/entities/language';
import { LanguageModule } from 'src/app/entities/language/language.module';
import { languageRoute } from './language.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [LanguageDetailsExtendedComponent, LanguageListExtendedComponent, LanguageNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [languageRoute, LanguageModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: LanguageService, useClass: LanguageExtendedService }],
})
export class LanguageExtendedModule {}
