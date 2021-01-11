import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  UserroleExtendedService,
  UserroleDetailsExtendedComponent,
  UserroleListExtendedComponent,
  UserroleNewExtendedComponent,
} from '../';
import { IUserrole } from 'src/app/admin/user-management/userrole';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('UserroleListExtendedComponent', () => {
  let fixture: ComponentFixture<UserroleListExtendedComponent>;
  let component: UserroleListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [UserroleListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [UserroleExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserroleListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          UserroleListExtendedComponent,
          UserroleNewExtendedComponent,
          NewComponent,
          UserroleDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'userrole', component: UserroleListExtendedComponent },
            { path: 'userrole/:id', component: UserroleDetailsExtendedComponent },
          ]),
        ],
        providers: [UserroleExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserroleListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
