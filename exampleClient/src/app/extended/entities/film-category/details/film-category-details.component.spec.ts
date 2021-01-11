import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import {
  FilmCategoryExtendedService,
  FilmCategoryDetailsExtendedComponent,
  FilmCategoryListExtendedComponent,
} from '../';
import { IFilmCategory } from 'src/app/entities/film-category';
describe('FilmCategoryDetailsExtendedComponent', () => {
  let component: FilmCategoryDetailsExtendedComponent;
  let fixture: ComponentFixture<FilmCategoryDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [FilmCategoryDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [FilmCategoryExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmCategoryDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          FilmCategoryDetailsExtendedComponent,
          FilmCategoryListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'filmcategory', component: FilmCategoryDetailsExtendedComponent },
            { path: 'filmcategory/:id', component: FilmCategoryListExtendedComponent },
          ]),
        ],
        providers: [FilmCategoryExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmCategoryDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
