import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { RentalExtendedService } from '../rental.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CustomerExtendedService } from 'src/app/extended/entities/customer/customer.service';
import { InventoryExtendedService } from 'src/app/extended/entities/inventory/inventory.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { RentalDetailsComponent } from 'src/app/entities/rental/index';

@Component({
  selector: 'app-rental-details',
  templateUrl: './rental-details.component.html',
  styleUrls: ['./rental-details.component.scss'],
})
export class RentalDetailsExtendedComponent extends RentalDetailsComponent implements OnInit {
  title: string = 'Rental';
  parentUrl: string = 'rental';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public rentalExtendedService: RentalExtendedService,
    public pickerDialogService: PickerDialogService,
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
      global,
      rentalExtendedService,
      pickerDialogService,
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
