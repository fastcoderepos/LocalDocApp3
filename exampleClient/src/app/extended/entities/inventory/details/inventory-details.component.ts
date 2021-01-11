import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { InventoryExtendedService } from '../inventory.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { InventoryDetailsComponent } from 'src/app/entities/inventory/index';

@Component({
  selector: 'app-inventory-details',
  templateUrl: './inventory-details.component.html',
  styleUrls: ['./inventory-details.component.scss'],
})
export class InventoryDetailsExtendedComponent extends InventoryDetailsComponent implements OnInit {
  title: string = 'Inventory';
  parentUrl: string = 'inventory';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public inventoryExtendedService: InventoryExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public filmExtendedService: FilmExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      inventoryExtendedService,
      pickerDialogService,
      errorService,
      filmExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
