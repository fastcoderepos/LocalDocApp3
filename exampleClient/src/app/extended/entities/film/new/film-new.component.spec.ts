import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { FilmExtendedService, FilmNewExtendedComponent } from '../';
import { IFilm } from 'src/app/entities/film';
import { NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('FilmNewExtendedComponent', () => {
  let component: FilmNewExtendedComponent;
  let fixture: ComponentFixture<FilmNewExtendedComponent>;

  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [FilmNewExtendedComponent, NewComponent, FieldsComp],
        imports: [TestingModule],
        providers: [FilmExtendedService, { provide: MAT_DIALOG_DATA, useValue: {} }],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmNewExtendedComponent);
      component = fixture.componentInstance;
      spyOn(component, 'manageScreenResizing').and.returnValue();
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    describe('', () => {
      beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [FilmNewExtendedComponent, NewComponent, FieldsComp].concat(EntryComponents),
          imports: [TestingModule],
          providers: [FilmExtendedService, { provide: MAT_DIALOG_DATA, useValue: {} }],
        });
      }));

      beforeEach(() => {
        fixture = TestBed.createComponent(FilmNewExtendedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });
    });
  });
});
