import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { FilmExtendedService, FilmDetailsExtendedComponent, FilmListExtendedComponent } from '../';
import { IFilm } from 'src/app/entities/film';
describe('FilmDetailsExtendedComponent', () => {
  let component: FilmDetailsExtendedComponent;
  let fixture: ComponentFixture<FilmDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [FilmDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [FilmExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          FilmDetailsExtendedComponent,
          FilmListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'film', component: FilmDetailsExtendedComponent },
            { path: 'film/:id', component: FilmListExtendedComponent },
          ]),
        ],
        providers: [FilmExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
