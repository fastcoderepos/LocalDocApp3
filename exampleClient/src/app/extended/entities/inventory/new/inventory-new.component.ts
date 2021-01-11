import { Component, OnInit, Inject } from '@angular/core';
import { InventoryExtendedService } from '../inventory.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { FilmExtendedService } from 'src/app/extended/entities/film/film.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { InventoryNewComponent } from 'src/app/entities/inventory/index';

@Component({
  selector: 'app-inventory-new',
  templateUrl: './inventory-new.component.html',
  styleUrls: ['./inventory-new.component.scss'],
})
export class InventoryNewExtendedComponent extends InventoryNewComponent implements OnInit {
  title: string = 'New Inventory';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<InventoryNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public inventoryExtendedService: InventoryExtendedService,
    public errorService: ErrorService,
    public filmExtendedService: FilmExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      dialogRef,
      data,
      global,
      pickerDialogService,
      inventoryExtendedService,
      errorService,
      filmExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
