import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  PaymentExtendedService,
  PaymentDetailsExtendedComponent,
  PaymentListExtendedComponent,
  PaymentNewExtendedComponent,
} from '../';
import { IPayment } from 'src/app/entities/payment';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('PaymentListExtendedComponent', () => {
  let fixture: ComponentFixture<PaymentListExtendedComponent>;
  let component: PaymentListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [PaymentListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [PaymentExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(PaymentListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          PaymentListExtendedComponent,
          PaymentNewExtendedComponent,
          NewComponent,
          PaymentDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'payment', component: PaymentListExtendedComponent },
            { path: 'payment/:id', component: PaymentDetailsExtendedComponent },
          ]),
        ],
        providers: [PaymentExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(PaymentListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
