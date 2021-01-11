import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  RentalExtendedService,
  RentalDetailsExtendedComponent,
  RentalListExtendedComponent,
  RentalNewExtendedComponent,
} from '../';
import { IRental } from 'src/app/entities/rental';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('RentalListExtendedComponent', () => {
  let fixture: ComponentFixture<RentalListExtendedComponent>;
  let component: RentalListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [RentalListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [RentalExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(RentalListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          RentalListExtendedComponent,
          RentalNewExtendedComponent,
          NewComponent,
          RentalDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'rental', component: RentalListExtendedComponent },
            { path: 'rental/:id', component: RentalDetailsExtendedComponent },
          ]),
        ],
        providers: [RentalExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(RentalListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
