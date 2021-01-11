import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  FilmActorExtendedService,
  FilmActorDetailsExtendedComponent,
  FilmActorListExtendedComponent,
  FilmActorNewExtendedComponent,
} from '../';
import { IFilmActor } from 'src/app/entities/film-actor';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('FilmActorListExtendedComponent', () => {
  let fixture: ComponentFixture<FilmActorListExtendedComponent>;
  let component: FilmActorListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [FilmActorListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [FilmActorExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmActorListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          FilmActorListExtendedComponent,
          FilmActorNewExtendedComponent,
          NewComponent,
          FilmActorDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'filmactor', component: FilmActorListExtendedComponent },
            { path: 'filmactor/:id', component: FilmActorDetailsExtendedComponent },
          ]),
        ],
        providers: [FilmActorExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(FilmActorListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
