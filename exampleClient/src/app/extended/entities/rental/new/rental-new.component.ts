import { Component, OnInit, Inject } from '@angular/core';
import { RentalExtendedService } from '../rental.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { CustomerExtendedService } from 'src/app/extended/entities/customer/customer.service';
import { InventoryExtendedService } from 'src/app/extended/entities/inventory/inventory.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { RentalNewComponent } from 'src/app/entities/rental/index';

@Component({
  selector: 'app-rental-new',
  templateUrl: './rental-new.component.html',
  styleUrls: ['./rental-new.component.scss'],
})
export class RentalNewExtendedComponent extends RentalNewComponent implements OnInit {
  title: string = 'New Rental';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<RentalNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public rentalExtendedService: RentalExtendedService,
    public errorService: ErrorService,
    public customerExtendedService: CustomerExtendedService,
    public inventoryExtendedService: InventoryExtendedService,
    public staffExtendedService: StaffExtendedService,
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
      rentalExtendedService,
      errorService,
      customerExtendedService,
      inventoryExtendedService,
      staffExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
