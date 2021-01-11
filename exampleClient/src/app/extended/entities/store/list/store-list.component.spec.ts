import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  StoreExtendedService,
  StoreDetailsExtendedComponent,
  StoreListExtendedComponent,
  StoreNewExtendedComponent,
} from '../';
import { IStore } from 'src/app/entities/store';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('StoreListExtendedComponent', () => {
  let fixture: ComponentFixture<StoreListExtendedComponent>;
  let component: StoreListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [StoreListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [StoreExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StoreListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          StoreListExtendedComponent,
          StoreNewExtendedComponent,
          NewComponent,
          StoreDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'store', component: StoreListExtendedComponent },
            { path: 'store/:id', component: StoreDetailsExtendedComponent },
          ]),
        ],
        providers: [StoreExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StoreListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
