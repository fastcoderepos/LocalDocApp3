import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { StoreExtendedService, StoreDetailsExtendedComponent, StoreListExtendedComponent } from '../';
import { IStore } from 'src/app/entities/store';
describe('StoreDetailsExtendedComponent', () => {
  let component: StoreDetailsExtendedComponent;
  let fixture: ComponentFixture<StoreDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [StoreDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [StoreExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StoreDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          StoreDetailsExtendedComponent,
          StoreListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'store', component: StoreDetailsExtendedComponent },
            { path: 'store/:id', component: StoreListExtendedComponent },
          ]),
        ],
        providers: [StoreExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(StoreDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
