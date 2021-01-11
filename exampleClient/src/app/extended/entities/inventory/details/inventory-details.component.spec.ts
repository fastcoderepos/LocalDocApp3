import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { InventoryExtendedService, InventoryDetailsExtendedComponent, InventoryListExtendedComponent } from '../';
import { IInventory } from 'src/app/entities/inventory';
describe('InventoryDetailsExtendedComponent', () => {
  let component: InventoryDetailsExtendedComponent;
  let fixture: ComponentFixture<InventoryDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [InventoryDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [InventoryExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          InventoryDetailsExtendedComponent,
          InventoryListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'inventory', component: InventoryDetailsExtendedComponent },
            { path: 'inventory/:id', component: InventoryListExtendedComponent },
          ]),
        ],
        providers: [InventoryExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
