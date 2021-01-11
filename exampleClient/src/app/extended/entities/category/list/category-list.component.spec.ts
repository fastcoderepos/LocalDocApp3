import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  CategoryExtendedService,
  CategoryDetailsExtendedComponent,
  CategoryListExtendedComponent,
  CategoryNewExtendedComponent,
} from '../';
import { ICategory } from 'src/app/entities/category';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('CategoryListExtendedComponent', () => {
  let fixture: ComponentFixture<CategoryListExtendedComponent>;
  let component: CategoryListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [CategoryListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [CategoryExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CategoryListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          CategoryListExtendedComponent,
          CategoryNewExtendedComponent,
          NewComponent,
          CategoryDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'category', component: CategoryListExtendedComponent },
            { path: 'category/:id', component: CategoryDetailsExtendedComponent },
          ]),
        ],
        providers: [CategoryExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CategoryListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
