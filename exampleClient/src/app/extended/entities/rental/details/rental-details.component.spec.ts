import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { RentalExtendedService, RentalDetailsExtendedComponent, RentalListExtendedComponent } from '../';
import { IRental } from 'src/app/entities/rental';
describe('RentalDetailsExtendedComponent', () => {
  let component: RentalDetailsExtendedComponent;
  let fixture: ComponentFixture<RentalDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [RentalDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [RentalExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(RentalDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          RentalDetailsExtendedComponent,
          RentalListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'rental', component: RentalDetailsExtendedComponent },
            { path: 'rental/:id', component: RentalListExtendedComponent },
          ]),
        ],
        providers: [RentalExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(RentalDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
