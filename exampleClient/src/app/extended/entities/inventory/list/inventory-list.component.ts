import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { InventoryExtendedService } from '../inventory.service';
import { InventoryNewExtendedComponent } from '../new/inventory-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { InventoryListComponent } from 'src/app/entities/inventory/index';

@Component({
  selector: 'app-inventory-list',
  templateUrl: './inventory-list.component.html',
  styleUrls: ['./inventory-list.component.scss'],
})
export class InventoryListExtendedComponent extends InventoryListComponent implements OnInit {
  title: string = 'Inventory';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public inventoryService: InventoryExtendedService,
    public errorService: ErrorService,
    public filmService: FilmExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      inventoryService,
      errorService,
      filmService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(InventoryNewExtendedComponent);
  }
}
