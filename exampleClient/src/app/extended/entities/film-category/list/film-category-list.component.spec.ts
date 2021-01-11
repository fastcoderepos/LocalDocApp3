import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  FilmCategoryExtendedService,
  FilmCategoryDetailsExtendedComponent,
  FilmCategoryListExtendedComponent,
  FilmCategoryNewExtendedComponent,
} from '../';
import { IFilmCategory } from 'src/app/entities/film-category';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('FilmCategoryListExtendedComponent', () => {
  let fixture: ComponentFixture<FilmCategoryListExtendedComponent>;
  let component: FilmCategoryListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [FilmCategoryListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [FilmCategoryExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmCategoryListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          FilmCategoryListExtendedComponent,
          FilmCategoryNewExtendedComponent,
          NewComponent,
          FilmCategoryDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'filmcategory', component: FilmCategoryListExtendedComponent },
            { path: 'filmcategory/:id', component: FilmCategoryDetailsExtendedComponent },
          ]),
        ],
        providers: [FilmCategoryExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmCategoryListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
