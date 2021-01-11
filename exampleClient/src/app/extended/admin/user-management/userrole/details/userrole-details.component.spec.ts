import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { UserroleExtendedService, UserroleDetailsExtendedComponent, UserroleListExtendedComponent } from '../';
import { IUserrole } from 'src/app/admin/user-management/userrole';
describe('UserroleDetailsExtendedComponent', () => {
  let component: UserroleDetailsExtendedComponent;
  let fixture: ComponentFixture<UserroleDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [UserroleDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [UserroleExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserroleDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          UserroleDetailsExtendedComponent,
          UserroleListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'userrole', component: UserroleDetailsExtendedComponent },
            { path: 'userrole/:id', component: UserroleListExtendedComponent },
          ]),
        ],
        providers: [UserroleExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserroleDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
