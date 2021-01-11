import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  CountryExtendedService,
  CountryDetailsExtendedComponent,
  CountryListExtendedComponent,
  CountryNewExtendedComponent,
} from '../';
import { ICountry } from 'src/app/entities/country';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('CountryListExtendedComponent', () => {
  let fixture: ComponentFixture<CountryListExtendedComponent>;
  let component: CountryListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [CountryListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [CountryExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CountryListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          CountryListExtendedComponent,
          CountryNewExtendedComponent,
          NewComponent,
          CountryDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'country', component: CountryListExtendedComponent },
            { path: 'country/:id', component: CountryDetailsExtendedComponent },
          ]),
        ],
        providers: [CountryExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CountryListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
