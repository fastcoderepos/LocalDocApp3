import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { StaffExtendedService, StaffDetailsExtendedComponent, StaffListExtendedComponent } from '../';
import { IStaff } from 'src/app/entities/staff';
describe('StaffDetailsExtendedComponent', () => {
  let component: StaffDetailsExtendedComponent;
  let fixture: ComponentFixture<StaffDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [StaffDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [StaffExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StaffDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          StaffDetailsExtendedComponent,
          StaffListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'staff', component: StaffDetailsExtendedComponent },
            { path: 'staff/:id', component: StaffListExtendedComponent },
          ]),
        ],
        providers: [StaffExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StaffDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
