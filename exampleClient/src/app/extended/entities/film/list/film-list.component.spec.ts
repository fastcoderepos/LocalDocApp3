import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  FilmExtendedService,
  FilmDetailsExtendedComponent,
  FilmListExtendedComponent,
  FilmNewExtendedComponent,
} from '../';
import { IFilm } from 'src/app/entities/film';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('FilmListExtendedComponent', () => {
  let fixture: ComponentFixture<FilmListExtendedComponent>;
  let component: FilmListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [FilmListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [FilmExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          FilmListExtendedComponent,
          FilmNewExtendedComponent,
          NewComponent,
          FilmDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'film', component: FilmListExtendedComponent },
            { path: 'film/:id', component: FilmDetailsExtendedComponent },
          ]),
        ],
        providers: [FilmExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
