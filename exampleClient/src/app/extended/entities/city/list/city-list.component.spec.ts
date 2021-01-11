import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  CityExtendedService,
  CityDetailsExtendedComponent,
  CityListExtendedComponent,
  CityNewExtendedComponent,
} from '../';
import { ICity } from 'src/app/entities/city';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('CityListExtendedComponent', () => {
  let fixture: ComponentFixture<CityListExtendedComponent>;
  let component: CityListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [CityListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [CityExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CityListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          CityListExtendedComponent,
          CityNewExtendedComponent,
          NewComponent,
          CityDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'city', component: CityListExtendedComponent },
            { path: 'city/:id', component: CityDetailsExtendedComponent },
          ]),
        ],
        providers: [CityExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CityListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
