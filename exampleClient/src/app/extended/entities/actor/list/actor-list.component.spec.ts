import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  ActorExtendedService,
  ActorDetailsExtendedComponent,
  ActorListExtendedComponent,
  ActorNewExtendedComponent,
} from '../';
import { IActor } from 'src/app/entities/actor';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('ActorListExtendedComponent', () => {
  let fixture: ComponentFixture<ActorListExtendedComponent>;
  let component: ActorListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ActorListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [ActorExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ActorListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          ActorListExtendedComponent,
          ActorNewExtendedComponent,
          NewComponent,
          ActorDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'actor', component: ActorListExtendedComponent },
            { path: 'actor/:id', component: ActorDetailsExtendedComponent },
          ]),
        ],
        providers: [ActorExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ActorListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
