import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import {
  UserpermissionExtendedService,
  UserpermissionDetailsExtendedComponent,
  UserpermissionListExtendedComponent,
} from '../';
import { IUserpermission } from 'src/app/admin/user-management/userpermission';
describe('UserpermissionDetailsExtendedComponent', () => {
  let component: UserpermissionDetailsExtendedComponent;
  let fixture: ComponentFixture<UserpermissionDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [UserpermissionDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [UserpermissionExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserpermissionDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          UserpermissionDetailsExtendedComponent,
          UserpermissionListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'userpermission', component: UserpermissionDetailsExtendedComponent },
            { path: 'userpermission/:id', component: UserpermissionListExtendedComponent },
          ]),
        ],
        providers: [UserpermissionExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserpermissionDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
