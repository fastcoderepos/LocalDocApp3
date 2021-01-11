import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { ActorExtendedService, ActorDetailsExtendedComponent, ActorListExtendedComponent } from '../';
import { IActor } from 'src/app/entities/actor';
describe('ActorDetailsExtendedComponent', () => {
  let component: ActorDetailsExtendedComponent;
  let fixture: ComponentFixture<ActorDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ActorDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [ActorExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ActorDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          ActorDetailsExtendedComponent,
          ActorListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'actor', component: ActorDetailsExtendedComponent },
            { path: 'actor/:id', component: ActorListExtendedComponent },
          ]),
        ],
        providers: [ActorExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ActorDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
