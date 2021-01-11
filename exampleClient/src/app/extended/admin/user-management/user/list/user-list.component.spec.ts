import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  UserExtendedService,
  UserDetailsExtendedComponent,
  UserListExtendedComponent,
  UserNewExtendedComponent,
} from '../';
import { IUser } from 'src/app/admin/user-management/user';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('UserListExtendedComponent', () => {
  let fixture: ComponentFixture<UserListExtendedComponent>;
  let component: UserListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [UserListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [UserExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          UserListExtendedComponent,
          UserNewExtendedComponent,
          NewComponent,
          UserDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'user', component: UserListExtendedComponent },
            { path: 'user/:id', component: UserDetailsExtendedComponent },
          ]),
        ],
        providers: [UserExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
