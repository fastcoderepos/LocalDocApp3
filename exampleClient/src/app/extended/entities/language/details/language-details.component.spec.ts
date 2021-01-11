import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { LanguageExtendedService, LanguageDetailsExtendedComponent, LanguageListExtendedComponent } from '../';
import { ILanguage } from 'src/app/entities/language';
describe('LanguageDetailsExtendedComponent', () => {
  let component: LanguageDetailsExtendedComponent;
  let fixture: ComponentFixture<LanguageDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [LanguageDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [LanguageExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(LanguageDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          LanguageDetailsExtendedComponent,
          LanguageListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'language', component: LanguageDetailsExtendedComponent },
            { path: 'language/:id', component: LanguageListExtendedComponent },
          ]),
        ],
        providers: [LanguageExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(LanguageDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
