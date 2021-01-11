import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  AddressExtendedService,
  AddressDetailsExtendedComponent,
  AddressListExtendedComponent,
  AddressNewExtendedComponent,
} from '../';
import { IAddress } from 'src/app/entities/address';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('AddressListExtendedComponent', () => {
  let fixture: ComponentFixture<AddressListExtendedComponent>;
  let component: AddressListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [AddressListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [AddressExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(AddressListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          AddressListExtendedComponent,
          AddressNewExtendedComponent,
          NewComponent,
          AddressDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'address', component: AddressListExtendedComponent },
            { path: 'address/:id', component: AddressDetailsExtendedComponent },
          ]),
        ],
        providers: [AddressExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(AddressListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
