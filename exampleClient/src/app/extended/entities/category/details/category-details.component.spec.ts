import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { CategoryExtendedService, CategoryDetailsExtendedComponent, CategoryListExtendedComponent } from '../';
import { ICategory } from 'src/app/entities/category';
describe('CategoryDetailsExtendedComponent', () => {
  let component: CategoryDetailsExtendedComponent;
  let fixture: ComponentFixture<CategoryDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [CategoryDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [CategoryExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CategoryDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          CategoryDetailsExtendedComponent,
          CategoryListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'category', component: CategoryDetailsExtendedComponent },
            { path: 'category/:id', component: CategoryListExtendedComponent },
          ]),
        ],
        providers: [CategoryExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CategoryDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
