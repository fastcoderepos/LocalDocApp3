import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { UserroleExtendedService, UserroleNewExtendedComponent } from '../';
import { IUserrole } from 'src/app/admin/user-management/userrole';
import { NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('UserroleNewExtendedComponent', () => {
  let component: UserroleNewExtendedComponent;
  let fixture: ComponentFixture<UserroleNewExtendedComponent>;

  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [UserroleNewExtendedComponent, NewComponent, FieldsComp],
        imports: [TestingModule],
        providers: [UserroleExtendedService, { provide: MAT_DIALOG_DATA, useValue: {} }],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserroleNewExtendedComponent);
      component = fixture.componentInstance;
      spyOn(component, 'manageScreenResizing').and.returnValue();
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    describe('', () => {
      beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [UserroleNewExtendedComponent, NewComponent, FieldsComp].concat(EntryComponents),
          imports: [TestingModule],
          providers: [UserroleExtendedService, { provide: MAT_DIALOG_DATA, useValue: {} }],
        });
      }));

      beforeEach(() => {
        fixture = TestBed.createComponent(UserroleNewExtendedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });
    });
  });
});
