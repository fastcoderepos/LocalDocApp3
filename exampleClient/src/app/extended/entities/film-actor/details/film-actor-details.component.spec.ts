import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { FilmActorExtendedService, FilmActorDetailsExtendedComponent, FilmActorListExtendedComponent } from '../';
import { IFilmActor } from 'src/app/entities/film-actor';
describe('FilmActorDetailsExtendedComponent', () => {
  let component: FilmActorDetailsExtendedComponent;
  let fixture: ComponentFixture<FilmActorDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [FilmActorDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [FilmActorExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmActorDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          FilmActorDetailsExtendedComponent,
          FilmActorListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'filmactor', component: FilmActorDetailsExtendedComponent },
            { path: 'filmactor/:id', component: FilmActorListExtendedComponent },
          ]),
        ],
        providers: [FilmActorExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmActorDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
