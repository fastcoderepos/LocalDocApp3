import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { PaymentExtendedService, PaymentDetailsExtendedComponent, PaymentListExtendedComponent } from '../';
import { IPayment } from 'src/app/entities/payment';
describe('PaymentDetailsExtendedComponent', () => {
  let component: PaymentDetailsExtendedComponent;
  let fixture: ComponentFixture<PaymentDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [PaymentDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [PaymentExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(PaymentDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          PaymentDetailsExtendedComponent,
          PaymentListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'payment', component: PaymentDetailsExtendedComponent },
            { path: 'payment/:id', component: PaymentListExtendedComponent },
          ]),
        ],
        providers: [PaymentExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(PaymentDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
