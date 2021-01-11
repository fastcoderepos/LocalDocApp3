import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  InventoryExtendedService,
  InventoryDetailsExtendedComponent,
  InventoryListExtendedComponent,
  InventoryNewExtendedComponent,
} from '../';
import { IInventory } from 'src/app/entities/inventory';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('InventoryListExtendedComponent', () => {
  let fixture: ComponentFixture<InventoryListExtendedComponent>;
  let component: InventoryListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [InventoryListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [InventoryExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          InventoryListExtendedComponent,
          InventoryNewExtendedComponent,
          NewComponent,
          InventoryDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'inventory', component: InventoryListExtendedComponent },
            { path: 'inventory/:id', component: InventoryDetailsExtendedComponent },
          ]),
        ],
        providers: [InventoryExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
