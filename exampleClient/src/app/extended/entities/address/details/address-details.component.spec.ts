import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { AddressExtendedService, AddressDetailsExtendedComponent, AddressListExtendedComponent } from '../';
import { IAddress } from 'src/app/entities/address';
describe('AddressDetailsExtendedComponent', () => {
  let component: AddressDetailsExtendedComponent;
  let fixture: ComponentFixture<AddressDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [AddressDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [AddressExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(AddressDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          AddressDetailsExtendedComponent,
          AddressListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'address', component: AddressDetailsExtendedComponent },
            { path: 'address/:id', component: AddressListExtendedComponent },
          ]),
        ],
        providers: [AddressExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(AddressDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
