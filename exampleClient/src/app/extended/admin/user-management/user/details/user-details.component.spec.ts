import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { UserExtendedService, UserDetailsExtendedComponent, UserListExtendedComponent } from '../';
import { IUser } from 'src/app/admin/user-management/user';
describe('UserDetailsExtendedComponent', () => {
  let component: UserDetailsExtendedComponent;
  let fixture: ComponentFixture<UserDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [UserDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [UserExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          UserDetailsExtendedComponent,
          UserListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'user', component: UserDetailsExtendedComponent },
            { path: 'user/:id', component: UserListExtendedComponent },
          ]),
        ],
        providers: [UserExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
