import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  UserpermissionExtendedService,
  UserpermissionDetailsExtendedComponent,
  UserpermissionListExtendedComponent,
  UserpermissionNewExtendedComponent,
} from '../';
import { IUserpermission } from 'src/app/admin/user-management/userpermission';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('UserpermissionListExtendedComponent', () => {
  let fixture: ComponentFixture<UserpermissionListExtendedComponent>;
  let component: UserpermissionListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [UserpermissionListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [UserpermissionExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserpermissionListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          UserpermissionListExtendedComponent,
          UserpermissionNewExtendedComponent,
          NewComponent,
          UserpermissionDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'userpermission', component: UserpermissionListExtendedComponent },
            { path: 'userpermission/:id', component: UserpermissionDetailsExtendedComponent },
          ]),
        ],
        providers: [UserpermissionExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserpermissionListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
