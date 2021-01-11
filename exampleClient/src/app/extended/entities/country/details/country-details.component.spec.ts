import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { CountryExtendedService, CountryDetailsExtendedComponent, CountryListExtendedComponent } from '../';
import { ICountry } from 'src/app/entities/country';
describe('CountryDetailsExtendedComponent', () => {
  let component: CountryDetailsExtendedComponent;
  let fixture: ComponentFixture<CountryDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [CountryDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [CountryExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CountryDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          CountryDetailsExtendedComponent,
          CountryListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'country', component: CountryDetailsExtendedComponent },
            { path: 'country/:id', component: CountryListExtendedComponent },
          ]),
        ],
        providers: [CountryExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CountryDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
