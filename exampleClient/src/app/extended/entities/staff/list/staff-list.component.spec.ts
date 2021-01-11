import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  StaffExtendedService,
  StaffDetailsExtendedComponent,
  StaffListExtendedComponent,
  StaffNewExtendedComponent,
} from '../';
import { IStaff } from 'src/app/entities/staff';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('StaffListExtendedComponent', () => {
  let fixture: ComponentFixture<StaffListExtendedComponent>;
  let component: StaffListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [StaffListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [StaffExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StaffListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          StaffListExtendedComponent,
          StaffNewExtendedComponent,
          NewComponent,
          StaffDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'staff', component: StaffListExtendedComponent },
            { path: 'staff/:id', component: StaffDetailsExtendedComponent },
          ]),
        ],
        providers: [StaffExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StaffListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
