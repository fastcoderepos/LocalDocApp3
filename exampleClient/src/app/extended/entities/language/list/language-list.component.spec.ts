import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  LanguageExtendedService,
  LanguageDetailsExtendedComponent,
  LanguageListExtendedComponent,
  LanguageNewExtendedComponent,
} from '../';
import { ILanguage } from 'src/app/entities/language';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('LanguageListExtendedComponent', () => {
  let fixture: ComponentFixture<LanguageListExtendedComponent>;
  let component: LanguageListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [LanguageListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [LanguageExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(LanguageListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          LanguageListExtendedComponent,
          LanguageNewExtendedComponent,
          NewComponent,
          LanguageDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'language', component: LanguageListExtendedComponent },
            { path: 'language/:id', component: LanguageDetailsExtendedComponent },
          ]),
        ],
        providers: [LanguageExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(LanguageListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
